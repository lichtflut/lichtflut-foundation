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
package de.lichtflut.infra.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.DOMBuilder;
import org.jdom.output.DOMOutputter;
import org.xml.sax.InputSource;

/**
 * Helper-Class for XML-Reading.
 *
 * Created: 29.08.2004
 * 
 * @author Oliver Tigges
 */
public class XmlReader {
	
	private static final XmlReader INSTANCE = new XmlReader(); 
	
	//------------------------------------------------------

	public static XmlReader instance(){
		return INSTANCE;
	}
	
	public static org.w3c.dom.Document toW3cDocument(Element jdomElement) throws JDOMException{
		DOMOutputter domOutputter = new DOMOutputter();
		Element clone = (Element) jdomElement.clone();
		clone.detach();
		return domOutputter.output(new Document(clone));
	}
	
	//------------------------------------------------------
	
	public Element getRootElement(String fileName){		
		return getRootElement(fileName, null);
	}
	
	public Element getRootElement(File file){		
		return getRootElement(file, null);
	}
	
	public Element getRootElement(String fileName, String encoding){
		File file = new File(fileName);
		return getRootElement(file, encoding);
	}
	
	public Element getRootElement(File file, String encoding) {
		try {
			FileInputStream fis = new FileInputStream(file);
			InputSource in = new InputSource(fis);
			if (encoding != null){
				in.setEncoding(encoding);
			}
			Element root = getRootElement(in);
			fis.close();
			return root;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File '" + file + "' does not exist!");
		} catch (IOException e) {
			throw new RuntimeException("Exception while processing file '" + file + "'");
		} 
	}
	
	public Element getRootElement(final InputSource in) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			documentBuilder.setEntityResolver(new XhtmlEntityResolver());
			org.w3c.dom.Document w3cDoc = documentBuilder.parse(in);
			org.jdom.Document jDoc = new DOMBuilder().build(w3cDoc);
			return jDoc.getRootElement();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
