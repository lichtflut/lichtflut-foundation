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
import java.util.Map;

import org.jdom.Document;

import de.lichtflut.infra.html.provider.XhtmlProvider;

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
public class MTIExtractor{

	
	//Members
	//Fields
	private HtmlDocument document;
	
	
	//Constructor
	public MTIExtractor(Document document){
		generateInformationStructure(document);
	}//end of Constructor
	

	private boolean generateInformationStructure(Document rawDoc) {
		this.document = new XhtmlProvider().create(rawDoc);
		generateTables(document.getBody());
		return true;
	}//End of Method generateInformationStructure() 

	//Generate the tableStructure
	private void generateTables(HtmlElement node){
		List<HtmlElement> children = node.getChildren();
		//if there are no children, get out of here!
		if(children==null || children.size()==0) return;
		if(node instanceof HtmlTable){
			//its a table, lets make a type-cast
			HtmlTable table = (HtmlTable) node;
			//initializing table
			
			HtmlFilter rowFilter = new HtmlFilter();
			rowFilter.addFilterRule(WellKnownElement.TR);
			List<HtmlElement> rows = new LinkedList<HtmlElement>();
			this.getElements(rowFilter,rows,table,1);
			
			HtmlFilter columnFilter = new HtmlFilter();
			columnFilter.addFilterRule(WellKnownElement.TD);
			columnFilter.addFilterRule(WellKnownElement.TH);
			table.table = new HtmlElement[rows.size()][];
			//fill table
			//iterate over rows
			for (int i = 0; i < rows.size(); i++) {
				List <HtmlElement> line = new LinkedList<HtmlElement>();
				this.getElements(columnFilter,line,rows.get(i),1);
				table.table[i] = new HtmlElement[line.size()];
				
				for (int j = 0; j < table.table[i].length; j++) {
					HtmlElement cell = line.get(j);
					
					//check for row or colspan
					Map <String,String>attribute = cell.getAttributes();
					if(attribute.containsKey("rowspan")&&attribute.get("rowspan")!=null){
						int rowspan = Integer.parseInt(attribute.get("rowspan"));
						for(int rowCnt=1;rowCnt<=(rowspan-1)&&i+rowCnt<rows.size();rowCnt++){
							rows.get(i+rowCnt).getChildren().add(j,cell);
							cell.getAttributes().remove("rowspan");
						}//end of for
					}else if(attribute.containsKey("colspan")){
						//int colspan = Integer.parseInt(attribute.get("colspan"));
					}
					//-------------------------------
					
					table.table[i][j] = cell;
				}//End of inner for
			}//End of outer for
		
		}//end if
		for (HtmlElement child : children) {
				generateTables(child); //Be recursive!
		}//end of for
		
	}//end of Method generateTables()
	
	//setters and getters
	
	public HtmlDocument getDocument() {
		return document;
	}//End of Method getDocument()
	
	
	public void getAllElements(List<HtmlElement> htmlElements){
		HtmlFilter filter = new HtmlFilter();
		filter.invert();
		this.getElements(filter, htmlElements);
	}//End of Method getElements()
	
	//recursive
	public void getElements(HtmlFilter filter, List<HtmlElement> htmlElements){
		this.getElements(filter, htmlElements, document.getHead());
		this.getElements(filter, htmlElements, document.getBody());
	}//End of Method getElements()
	
	public void getElements(HtmlFilter filter,
		   List<HtmlElement> htmlElements,HtmlElement node,int depth){
		this.getElements(filter,htmlElements,node,depth,true);
	}
	
	public void getElements(HtmlFilter filter,
			   List<HtmlElement> htmlElements,HtmlElement node){
			this.getElements(filter,htmlElements,node,0,false);
	}//End of Method getElements
	
	private void getElements(HtmlFilter filter,
		   List<HtmlElement> htmlElements,HtmlElement node, int depth,boolean depthFlag){
		if(htmlElements==null||filter==null||(depthFlag&&depth<0)) return;
		if(node==null) return;
		if(WellKnownElement.isValue((node.getName())))
			if(filter.isFilterRule(WellKnownElement.forValue(node.getName()))){
				htmlElements.add(node);
			}
		for(HtmlElement child:node.getChildren()){
			getElements(filter,htmlElements,child,(depth-1),depthFlag);
		}
	}//End of Method getElements
	
	
	public String getPlainText(boolean normalized){
		return getPlainText(document.getHead(),normalized) + getPlainText(document.getBody(),normalized);
	}//End of Method getPlainText
	
	public String getPlainText(HtmlElement node, boolean normalized){
		return getPlainText(node,new HtmlFilter(),normalized);
	}//End of Method getPlainText();
	
	public String getPlainText(HtmlElement node,HtmlFilter ignore,boolean normalized){
		HtmlFilter filter = new HtmlFilter();
		filter.addFilterRule(WellKnownElement.TEXT);
		List<HtmlElement> list = new LinkedList <HtmlElement>() ;
		getElements(filter,list,node);
		StringBuffer buf = new StringBuffer();
		for(HtmlElement elem: list){
			if(elem.getParent()!=null&& WellKnownElement.isValue(elem.getParent().getName())){
				if(!ignore.isFilterRule(WellKnownElement.forValue(elem.getParent().getName())))
					if(normalized) buf.append(((HtmlText)elem).getNormalizedText());
					else buf.append(elem.getText());
			}else buf.append(elem.getText());
			
		}//end of for
		return buf.toString();
	}//end of Method getPlainText()
	
	
	public HtmlTable[] getTables(HtmlElement node){
		List<HtmlElement> list = new LinkedList <HtmlElement>();
		HtmlFilter filter = new HtmlFilter();
		filter.addFilterRule(WellKnownElement.TABLE);
		getElements(filter,list,node);
		Object[] elements = list.toArray();
		HtmlTable[] tables = new HtmlTable[elements.length];
		for (int i = 0; i < tables.length; i++) {
			tables[i] = (HtmlTable) elements[i];
		}
		return tables;
	}//End of Method getTables()
	
	public HtmlTable[] getTables(){	
		return(getTables(document.getBody()));
	}//End of Method getTables()
	

	public void setDocument(Document document) {
		generateInformationStructure(document);
	}//End of Method setDocument()

	
}//End of class MTIExtractor
