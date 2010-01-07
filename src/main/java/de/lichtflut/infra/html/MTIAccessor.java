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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.jdom.Element;
import org.xml.sax.InputSource;

import de.lichtflut.infra.html.provider.XhtmlProvider;
import de.lichtflut.infra.io.UrlReader;
import de.lichtflut.infra.xml.XmlReader;

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
public class MTIAccessor {

	public static MTIExtractor generateExtractor(File file) throws FileNotFoundException{
		return generateExtractor(new FileReader(file));
	}
	
	public static MTIExtractor generateExtractor(Reader reader){
		Element rootElement = XmlReader.instance().getRootElement(new InputSource(reader));
		XhtmlProvider provider = new  XhtmlProvider();
		provider.create(rootElement.getDocument());
		return new MTIExtractor(rootElement.getDocument());
	}
	
	public static MTIExtractor generateExtractor(URL url) throws IOException{
		return generateExtractor(new UrlReader().read(url.toString()));
	}

	//Members
	private MTIExtractor extractor;
	private List<HtmlElement> resultList = new LinkedList<HtmlElement>();
	private HtmlElement rootNode;
	
	
	
	public List<HtmlElement> getResultList() {
		return resultList;
	}

	public void setResultList(List<HtmlElement> resultList) {
		this.resultList = resultList;
	}
	
	public HtmlElement getParent(HtmlElement node, HtmlFilter filter){
		if(node==null) return null;
		if(WellKnownElement.isValue((node.getName())))
			if(filter.isFilterRule(WellKnownElement.forValue(node.getName())))
				return node;
		return getParent(node.getParent(), filter);
	}//End of Method getParent()
	
	
	public HtmlElement getParent(HtmlElement node){
		return node.getParent();
	}

	public HtmlElement getRootNode() {
		return rootNode;
	}

	public void setRootNode(HtmlElement rootNode) {
		this.rootNode = rootNode;
	}

	//Constructor
	public MTIAccessor(MTIExtractor extractor){
		this.extractor = extractor;
		rootNode = extractor.getDocument();
	}//end of MTIAccessor

	public MTIExtractor getExtractor() {
		return extractor;
	}

	public void setExtractor(MTIExtractor extractor) {
		this.extractor = extractor;
	}

	public List<HtmlElement> getResult(HtmlFilter filter) {
		resultList = new LinkedList<HtmlElement>();
		extractor.getElements(filter,resultList,rootNode);
		return resultList;
	}
	
	
}//End of Class MTIAccessor
