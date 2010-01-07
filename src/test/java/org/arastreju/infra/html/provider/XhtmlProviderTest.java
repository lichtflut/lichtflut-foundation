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
/**
 * 
 */
package org.arastreju.infra.html.provider;

import java.io.IOException;
import java.io.Reader;

import junit.framework.TestCase;

import org.jdom.Element;
import org.xml.sax.InputSource;

import de.lichtflut.infra.html.HtmlDocument;
import de.lichtflut.infra.html.provider.XhtmlProvider;
import de.lichtflut.infra.io.UrlReader;
import de.lichtflut.infra.logging.Log;
import de.lichtflut.infra.xml.XmlReader;

/**
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 * 
 * @author Oliver Tigges
 * 
 * Created: 24.01.2009
 * 
 * Description: 
 */
public class XhtmlProviderTest extends TestCase {
	
	public void testWiktionary(){
		try {
			Reader reader = new UrlReader().read("http://de.wiktionary.org/wiki/Philologe");
			Element rootElement = XmlReader.instance().getRootElement(new InputSource(reader));
			XhtmlProvider provider = new  XhtmlProvider();
			HtmlDocument document = provider.create(rootElement.getDocument());

			Log.info(this, document.getBody().toString(""));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
