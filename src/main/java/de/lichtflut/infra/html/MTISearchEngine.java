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
package de.lichtflut.infra.html;

import java.util.LinkedList;
import java.util.List;

import de.lichtflut.infra.data.Tuple;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 * 
 * <p>
 * 	Created 07.07.2009
 * </p>
 *
 * @author Nils Bleisch
 */
public final class MTISearchEngine {

	//Members
	//fields
	MTIExtractor extractor;
	public static final int SEARCH_MASK_TEXT_FLAG=1;
	public static final int SEARCH_MASK_ATTRIBUTE_FLAG=2;
	
	
	/*
	 * private defaultConstructor,
	 * cause it shouldnt be allowed
	 *  to instantiate a MTISearchEngine-Object
	 */
	private MTISearchEngine(){}
	
	
	public static List<HtmlElement> searchFor(SearchContext context,String keyword,HtmlFilter filter, int depth,int mask){
			List <String> keywords = new LinkedList<String>();
			keywords.add(keyword);
			return searchFor(context,keywords,filter,depth,mask);
	}//Method searchFor() end
	
	
	public static List<HtmlElement> searchFor(SearchContext context,List<String> keywords,HtmlFilter filter, int depth, int mask){
		List<HtmlElement> result = context.getAccessor().getResult(filter);
		List<HtmlElement> output = new LinkedList<HtmlElement>();
		for(HtmlElement elem: result)
			searchRecursive(elem,keywords,output,depth,mask);
		return output;
	}
	
	private static void searchRecursive(HtmlElement node,List<String> keywords,List<HtmlElement>output, int depth, int mask){

		if(depth==0) return;
			for(String keyword: keywords){ //iterate over keywords
				if(((SEARCH_MASK_TEXT_FLAG & mask)==SEARCH_MASK_TEXT_FLAG)&& node instanceof HtmlText && node.getText().toLowerCase().contains(keyword.toLowerCase().subSequence(0,keyword.length()))){
					if(!output.contains(node)) output.add(node); //check if this node already exsits
				}else if((SEARCH_MASK_ATTRIBUTE_FLAG & mask)==SEARCH_MASK_ATTRIBUTE_FLAG && (node.getAttributes().containsKey(keyword) || node.getAttributes().containsValue(keyword))){
					if(!output.contains(node)) output.add(node); //check if this node already exsits
				}
			}
			if(node.getChildren()!=null){
				for(HtmlElement elem: node.getChildren()){
					searchRecursive(elem,keywords,output,(depth<0 ? -1 : (depth-1)),mask);
				}//end of for
			}//end of if
	}//End of methodSearchRecursive
	

	public static Tuple<Integer,Integer> getFirstTableIndex(SearchContext context,HtmlTable table, String keyword){
		HtmlElement[][] cells = table.table;
		if(cells==null) return null;
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				String cellText = context.getAccessor().getExtractor().getPlainText(cells[i][j],false);
				if(cellText.toLowerCase().contains(keyword.subSequence(0,keyword.length())))
					return new Tuple<Integer, Integer>(i,j);
			}//end of inner for
		}//end of outer for
		return null;
	}
	
	public static Tuple<Integer,Integer> getLastTableIndex(SearchContext context, HtmlTable table, String keyword){
		return null;
	}//end of Method getLastTableIndex()
	
	
	public static SearchContext getSearchContext(){
		return getSearchContext(null);
	}
	
	public static SearchContext getSearchContext(MTIAccessor accessor){
		SearchContext context=new SearchContext();
		context.setAccessor(accessor);
		return context;
	}
	
	
	public static class SearchContext{
		private MTIAccessor accessor = null;
		public boolean ROW_PRIORITY = true;
		//priva
		public MTIAccessor getAccessor() {
			return accessor;
		}
		public void setAccessor(MTIAccessor accessor) {
			this.accessor = accessor;
		}
	}//end of class 
	
	
}//Class of MTISearchEngine end
