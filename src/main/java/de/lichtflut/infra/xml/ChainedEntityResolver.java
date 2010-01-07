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
import java.io.InputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.lichtflut.infra.io.SystemResourceLoader;

/**
 * [TODO Insert description here.]
 * 
 * Created: 30.06.2009
 *
 * @author Oliver Tigges 
 */
public abstract class ChainedEntityResolver implements EntityResolver {
	
	private final EntityResolver successor;
	
	// ------------------------------------------------------
	
	/**
	 * Default Constructor. No successor in chain.
	 */
	public ChainedEntityResolver() {
		this.successor = null;
	}

	/**
	 * Constructor.
	 * @param successor The succeeding {@link EntityResolver} used if this one fails.
	 */
	public ChainedEntityResolver(final EntityResolver successor) {
		this.successor = successor;
	}
	
	// ------------------------------------------------------
	
	/* (non-Javadoc)
	 * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
	 */
	public abstract InputSource resolveEntity(String publicId, String systemId) 
		throws SAXException, IOException;

	/**
	 * calls next resolver in chain.
	 * @param publicId
	 * @param systemId
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public InputSource next(String publicId, String systemId) throws SAXException, IOException{
		if (successor != null){
			return successor.resolveEntity(publicId, systemId);
		} else {
			return null;
		}
	}
	
	/**
	 * opens an input stream to the resource specified by given URI.
	 * @param uri
	 * @return
	 */
	public InputStream loadResource(final String uri){
		return SystemResourceLoader.INSTANCE.loadResource(uri);
	}

}
