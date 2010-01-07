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

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.lichtflut.infra.io.SystemResourceLoader;

/**
 * Resolver for XmlEntities of XHMTL documents.
 * 
 * Inspired by: http://code.google.com/p/java-xhtml-cache-dtds-entityresolver/
 * 
 * Created: 30.06.2009
 *
 * @author Oliver Tigges 
 */
public class XhtmlEntityResolver extends ChainedEntityResolver {
	
    static final String XHTML_PREFIX = "http://www.w3.org/TR/xhtml1/DTD/";
    static final String XHTML_ROOT_DIR = SystemResourceLoader.RESOURCE_PREFIX + "xhtml";
    
    // ------------------------------------------------------
    
    /**
     * Constructor.
     * @param next The successor of this resolver. 
     */
    public XhtmlEntityResolver(EntityResolver next) {
       super(next);
    }

    /**
     * Default constructor with no successor.
     */
    public XhtmlEntityResolver() {
    	super();
    }

    // ------------------------------------------------------
    
    /* (non-Javadoc)
	 * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
	 */
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        if(systemId.startsWith(XHTML_PREFIX)) {
        	final String localName = XHTML_ROOT_DIR + "/" + systemId.substring(XHTML_PREFIX.length());
            InputSource inputSource = new InputSource(systemId);
            inputSource.setPublicId(publicId);
            inputSource.setByteStream(loadResource(localName));
            return inputSource;
        }
       return next(publicId, systemId);
    }
    
}
