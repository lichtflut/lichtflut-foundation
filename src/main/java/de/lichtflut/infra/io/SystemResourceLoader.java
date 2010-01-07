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
package de.lichtflut.infra.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import de.lichtflut.infra.logging.Log;

/**
 * Technical helper class for loading of resources.
 * 
 * Created: 27.10.2008
 *
 * @author Oliver Tigges
 */
public class SystemResourceLoader {
	
	public static final String RESOURCE_PREFIX = "resource:";
	
	public static SystemResourceLoader INSTANCE = new SystemResourceLoader();
	
	//-----------------------------------------------------

	/**
	 * Returns the only instance.
	 */
	public static SystemResourceLoader getInstance() {
		return INSTANCE;
	}

	//-----------------------------------------------------
	
	/**
	 * Checks if given Resource exists.
	 * @param uri The URI of the resource.
	 * @return The URL to that resource.
	 * @throws ResourceException
	 */
	public boolean exists(final String uri) throws ResourceException { 
		return findResource(uri) != null;
	}
	
	/**
	 * Finds a resource by u URI and provides the corresponding URL.
	 * @param uri The URI of the resource.
	 * @return The URL to that resource.
	 * @throws ResourceException
	 */
	public URL findResource(final String uri) throws ResourceException { 
		if (uri.startsWith(RESOURCE_PREFIX)){
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			URL url = cl.getResource(uri.substring(RESOURCE_PREFIX.length()));
			Log.info(this, "found resource: " + url);
			return url;
		} else {
			try {
				return new URL("file:" + uri);
			} catch (MalformedURLException e) {
				throw new ResourceException(e);
			}
		}	
	}
	
	/**
	 * Provides an {@link InputStream} to the resource identified by given URI. 
	 * @param uri The URI of the resource.
	 * @return The input stream to that resource.
	 * @throws ResourceException
	 */
	public InputStream loadResource(final String uri) throws ResourceException { 
		try {
			
			if (uri.startsWith(RESOURCE_PREFIX)){
				ClassLoader cl = Thread.currentThread().getContextClassLoader();
				URL url = cl.getResource(uri.substring(RESOURCE_PREFIX.length()));
				if (url == null){
					throw new ResourceException("Resource with URL '" + uri + "' not found by ClassLoader " + cl.toString());
				}
				Log.debug(this, "found resource: " + url);
				return url.openStream();
			} else {
				return new FileInputStream(uri);
			}
			
		} catch (IOException e) {
			throw new ResourceException(e);
		}
	}
	
	/**
	 * Load java property file.
	 * @param uri The URI of the properties file.
	 * @return The properties object.
	 * @throws ResourceException
	 */
	public Properties loadProperties(final String uri) throws ResourceException {
		InputStream in = loadResource(uri);
		Properties props = new Properties();
		try {
			props.load(in);
			in.close();
		} catch (IOException e) {
			throw new ResourceException(e);
		}
		return props;
	}
	
}
