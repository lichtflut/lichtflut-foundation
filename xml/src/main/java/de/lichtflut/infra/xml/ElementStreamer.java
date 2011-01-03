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

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.jdom.Element;
import org.jdom.filter.Filter;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.InputSource;

/**
 * Streams elements from in to out stream.
 * 
 * Created: 24.07.2009
 *
 * @author Oliver Tigges
 */
public class ElementStreamer {
	
	private final Filter filter;
	
	// -----------------------------------------------------

	/**
	 * Creates a new {@link ElementStreamer} using the given filter.
	 */
	public ElementStreamer(final Filter filter) {
		this.filter = filter;
	}

	// -----------------------------------------------------

	/**
	 * Processes input stream to out stream.
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public void process(final InputSource in, final OutputStream out) throws IOException{
		final Element root = XmlReader.instance().getRootElement(in);
		List<Element> elements = root.getChildren();
		process(elements, out);
	}
	
	/**
	 * Processes elements stream to out stream.
	 * @throws IOException 
	 */
	public void process(final List<Element> elements, final OutputStream out) throws IOException{
		final XMLOutputter outputter = new XMLOutputter();
		final Format format = Format.getCompactFormat();
		outputter.setFormat(format);
		for (Element element : elements) {
			if (filter.matches(element)){
				outputter.output(element, out);
			}
		}
	}
	
}
