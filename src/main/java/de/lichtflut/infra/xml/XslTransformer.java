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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom.Element;
import org.jdom.JDOMException;

import de.lichtflut.infra.xml.XmlReader;

/**
 * 
 * Helper-Class for XML-Transformation.
 * 
 * Created: 30.06.2005 
 * 
 * @author Oliver Tigges 
 */
public class XslTransformer {
	
	private final Transformer transformer;
	
	// -- CONSTRUCTORS ------------------------------------

	public XslTransformer(String template)
			throws TransformerConfigurationException,
			TransformerFactoryConfigurationError {
		
		this(new File(template));
	}

	public XslTransformer(File template)
			throws TransformerConfigurationException,
			TransformerFactoryConfigurationError {
		
		this(new StreamSource(template));
	}

	public XslTransformer(StreamSource templateStreamSource)
			throws TransformerConfigurationException,
			TransformerFactoryConfigurationError {
		
		transformer = TransformerFactory.newInstance().newTransformer(templateStreamSource);
		configureTransformer();
	}

	// ------------------------------------------------------

	public void transform(Source source, File targetFile)
			throws TransformerException, FileNotFoundException {
		System.out.println("Generating: " + targetFile);
		transformer.transform(source, new StreamResult(new FileOutputStream(targetFile)));
	}

	public void transform(File sourceFile, File targetFile)
			throws TransformerException, FileNotFoundException {
		transform(new StreamSource(sourceFile), targetFile);
	}

	public void transform(Element jdomElement, File targetFile)
			throws TransformerException, JDOMException, FileNotFoundException {
		transform(XmlReader.toW3cDocument(jdomElement), targetFile);
	}

	public void transform(org.w3c.dom.Document w3cDocument, File targetFile)
			throws TransformerException, FileNotFoundException {
		transform(new DOMSource(w3cDocument), targetFile);
	}

	// ------------------------------------------------------

	private void configureTransformer() {
		transformer.setOutputProperty("encoding", "UTF-8");
	}


}
