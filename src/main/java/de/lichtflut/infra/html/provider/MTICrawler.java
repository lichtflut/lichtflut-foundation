/*
 * Copyright (C) 2009 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.infra.html.provider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.lichtflut.infra.html.MTIAccessor;


/**
 * <p>
 *   This Class is providing specified information for a Set of defined targets
 * 	It could be also used as Thread
 * 	TODO: There could be a few problems with Mutual Exclusions.
 * 	Have a look at LinkedList-function-callOrder and behaviour
 * </p>
 * 
 * <p>
 * 	Created 04.06.2009
 * </p>
 *
 * @author Nils Bleisch
 */
public class MTICrawler implements Runnable{
	
	//-- MEMBER-FIELDS -------------------------------------
	
	/** The raw data, which is collected by 
	 * all the crawler-threads is already "preprocessed" (DOM-Structure)
	 * and available/accessable via MTIAccessor-Objects.
	 * These accessors are added and stored in rawDataAccessors.
	 * rawDataAccessor can also be set by an extern function call,
	 * so a HTTP-Request isnt the only way to extracting information through
	 * X-HTML-Markup. 
	 */
	private List<MTIAccessor> rawDataAccessors; 
	
	//Remove-Criterium for "idling" Threads
	private final int MAX_BUFFER_CNT = 4;
	//private final ArastrejuGate gate;
	//List of all Helper-Threads
	private List<CrawlersLittleHelper> helperThreads;
	//Queue of Threads, which are ready
	private List<CrawlersLittleHelper> rdyQueue; 
	/** This Map is holding the errors/exceptions,
	 * occured while crawling for specified targets
	 * One entry consists of the target-string (key) and the occurred Exception 
	 */
	private Map<String, Exception> occurredErrors = new HashMap<String, Exception>();
	
	private List<Object> targets=null;
	
	private int maxThreadCnt=0;
	
	private MTICrawlerExtractionSpec extrationSpec;
	
	//-- CONSTRUCTORS -------------------------------------

	/**
	 * Constructor, initializing the Object
	 * Created: 04.06.2009
	 * @author Nils Bleisch
	 * @param  int  specifies the maximum amount of crawler-threads,
	 * a zero means unlimited
	 * Created: 04.06.2009
	 * @author Nils Bleisch
	 */
	public MTICrawler(int maxThreadCnt,MTICrawlerExtractionSpec extractionSpec){
		//initializing field
		this.maxThreadCnt = Math.abs(maxThreadCnt);
		this.maxThreadCnt=maxThreadCnt;
		this.rawDataAccessors = new LinkedList<MTIAccessor>();
		this.rdyQueue = new LinkedList<CrawlersLittleHelper>();
		this.helperThreads = new LinkedList<CrawlersLittleHelper>();
		this.extrationSpec = extractionSpec;
	}//end of constructor
	
	
	//-- PROCESSING-METHODS -------------------------------------
	/**
	 * crawl-Method, starts the crawl-process,
	 * adding and removing new helperThreads, if possible
	 * @param  List targets, target-type: could be:
	 * io.Reader
	 * io.File
	 * net-URL
	 * Created: 04.06.2009
	 * @author Nils Bleisch
	 */
	public void crawl(List<Object> targets){
		while(targets!=null&&targets.size()!=0 || helperThreads.size()!=0){
				
			/*if there is no accessor available, 
			 * add a new helperThread to accelerate it
			 */
			if(rawDataAccessors.size()==0&&rdyQueue.size()==0 && targets.size()!=0){
				if(maxThreadCnt==0||maxThreadCnt>helperThreads.size()){
					CrawlersLittleHelper helper = new CrawlersLittleHelper(this,helperThreads.size());
					helper.setTarget(targets.remove(0));
					new Thread(helper).start();
					helperThreads.add(helper);
				}//end of if
				continue;
			}//end of if
			//iterate over idling helpers and setting up a new target
			while(rdyQueue.size()!=0){
				if(rawDataAccessors.size()>=MAX_BUFFER_CNT&&rdyQueue.size()>=MAX_BUFFER_CNT || targets.size()==0){
					CrawlersLittleHelper helper = rdyQueue.remove(0);
					//terminate this Thread
					helper.setTerminateCondition(true);
					//remove Thread from Thread-List
					helperThreads.remove(helper);
				}//end of i
				//if there is no more target available
				if(targets.size()==0) break; 
				//remove the helper on the first position in queue and set a new target
				rdyQueue.remove(0).setTarget(targets.remove(0));
				//Get accessor
				if(rawDataAccessors.size()!=0){
					MTIAccessor accessor = rawDataAccessors.remove(0);
					//Call the specified extration-specification for the given context
					extrationSpec.extractSpecifiedInformation(accessor);
				}
			}//end of while
		}//end of while
		stopAndRemoveThreads();
		
		
		
		while(rawDataAccessors.size()!=0){
			MTIAccessor accessor = rawDataAccessors.remove(0);
			//Call the specified extration-specification for the given context
			extrationSpec.extractSpecifiedInformation(accessor);
		}
	}//end of Method crawl()
	
	
	/**
	 * This Method is removing all Threads (CrawlersLittleHelper)
	 * If there is an active Thread, it would be stopped
	 * adding and removing new helperThreads, if possible
	 * Created: 09.06.2009
	 * @author Nils Bleisch
	 */
	public void stopAndRemoveThreads(){
		//Terminate all Threads
		for(CrawlersLittleHelper helperThread: helperThreads){
			helperThread.setTerminateCondition(true);
		}//end of for
		//remove Threads
		helperThreads.removeAll(helperThreads);
		rdyQueue.retainAll(rdyQueue);
	}//end of Method stopThreads()
	
	public void run() {
		crawl(getTargets());
	}//end of Method run()
	
	//-- GETTERS -------------------------------------
	
	private List<Object> getTargets() {
		return targets;
	}//end of MEthod getTargets()


	public List<MTIAccessor> getRawDataAccessors() {
		return rawDataAccessors;
	}//end of Method getRawDataAccessors()
	

	public List<CrawlersLittleHelper> getRdyQueue() {
		return rdyQueue;
	}//end of Method getRdyQueue
	
	public List<CrawlersLittleHelper> getHelperThreads() {
		return helperThreads;
	}//end of Method getHelperThreads
	
	public Map<String, Exception> getOccurredErrors() {
		return occurredErrors;
	}//end of Method getOccuredErrors()

	
	//-- SETTERS -------------------------------------
	
	public void setTargets(final List<Object> targets){
		this.targets = targets;
	}//end of Method setTargets
	
	//-- INNER-CLASSES -------------------------------------
	/**
	 * inner-class, used as Thread
	 * Delivers MTIAccessor-Objects
	 * to MTICrawler for specified target-URL's
	 * Created: 04.06.2009
	 * @author Nils Bleisch
	 */
	private static class CrawlersLittleHelper implements Runnable{
		
		
		//-- MEMBERS -------------------------------------
		//-- FIELDS -------------------------------------
		
		private MTICrawler crawlerManager;
		/*Specified target, as Object
		*  should be:
		*  io.Reader
		*  io.File
		*  net.URL
		*/
		private Object target;
		private boolean terminateCondition=false;


		//-- CONSTRUCTOR -------------------------------------
		public CrawlersLittleHelper(MTICrawler manager, int helperID){
			//initializing
			this.crawlerManager=manager;
		}//end of constructor
		
		
		//-- PROCESSING-METHODS -------------------------------------
		/**
		 * Run method to crawl for specified targets
		 * The result is available as an MTIAccessor-Object
		 * and is stored in rawDataAccessor-Collection of MTICrawler 
		 * Created: 03.06.2009
		 * @author Nils Bleisch
		 */
		public void run() {
			while(!terminateCondition){
				//Waiting for new assignment, if target is null:continue
				if(target==null) continue;
				//generate an MTIAccessor
				MTIAccessor accessor=null;
				try{
					//Check the type of target and generate a specific accessor
					if(target instanceof Reader)
						accessor = new MTIAccessor(MTIAccessor.generateExtractor((Reader)target));
					else if(target instanceof File)
							accessor = new MTIAccessor(MTIAccessor.generateExtractor((File)target));
					else if(target instanceof URL){
							//ToDo: URL-Encoding
							accessor = new MTIAccessor(MTIAccessor.generateExtractor(((URL)target)));
					}else{
						crawlerManager.getOccurredErrors().put(target.toString(),
																new Exception("Type of Target:" +
																		      target.getClass().getName() +
																		      " is not supported"));
					}//end of else
				}catch(FileNotFoundException any){
					crawlerManager.getOccurredErrors().put(target.toString(), any);
					target=null;
					crawlerManager.getRdyQueue().add(this);
					continue;
				}catch(IOException any){
					crawlerManager.getOccurredErrors().put(target.toString(), any);
					target=null;
					crawlerManager.getRdyQueue().add(this);
					continue;
				}//end of catch
				crawlerManager.getRawDataAccessors().add(accessor);
				//Set this Helper to "idle"
				//set target to null
				target=null;
				crawlerManager.getRdyQueue().add(this);
			}//end of while
		}//end of Method run()
		
		//-- SETTERS -------------------------------------
		
		//Overloaded
		public void setTarget(Object target){
			this.target = target;
		}//end of Method setTarget()
		
		public void setTerminateCondition(boolean condition){
			terminateCondition = condition;
		}//end of Method setTerminateCondition
		
	}//end of inner class CrawlerLittleHelper
	
	
}//end of class MTICrawler
