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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Reads content from a URL.
 * 
 * Created: 22.02.2008 
 *
 * @author Oliver Tigges
 */
public class UrlReader {
	
	/**
	 * Returns a Reader for given URL.
	 * 
	 * @param target
	 * @return
	 * @throws IOException
	 */
	public Reader read(String target) throws IOException{
		URL url = new URL(target);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String contentType = conn.getHeaderField("Content-Type");	
		String encoding = getEncoding(contentType);
		InputStreamReader in = new InputStreamReader(conn.getInputStream(), Charset.forName(encoding));
		return in;
	}
	
	/**
	 * Returns an {@link InputStream} for given URL.
	 * 
	 * @param target
	 * @return
	 * @throws IOException
	 */
	public InputStream stream(String target) throws IOException{
		URL url = new URL(target);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		return conn.getInputStream();
	}
	
	/**
	 * Returns the content of given URL as String.
	 * 
	 * @param target
	 * @return
	 * @throws IOException
	 */
	public String loadContent(String target) throws IOException{		
		Reader in = read(target);
		StringBuffer sb = new StringBuffer();
		int read = 0;
		char[] chars = new char[1000];
		while ( (read = in.read(chars)) > 0){	
			sb.append(chars, 0, read);	
		}
		in.close();
		return sb.toString();
	}
	
	//-----------------------------------------------------
	
	private String getEncoding(String contentType){
		String encoding = "ISO-8859-1";
		int pos = contentType.indexOf("charset=");
		if ( pos > 0 )
			 encoding = contentType.substring( pos + "charset=".length());
		return encoding;
	}
}
