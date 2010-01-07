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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Reads complete files or input streams.
 * 
 * Created: 01.05.2008 
 *
 * @author Oliver Tigges
 */
public class CompleteReader {
	
	public static final int DEFAULT_BLOCK_SIZE = 1000;
	public static final String DEFAULT_ENCODING = "UTF-8";

	public CompleteReader(){}
	
	//-----------------------------------------------------

	public String toString(File file) throws IOException {
		return toString(new FileInputStream(file));
	}
	
	public String toString(InputStream in) throws IOException {
		return toString(in, DEFAULT_ENCODING);
	}
	
	public String toString(InputStream in, String encoding) throws IOException {
		StringBuffer sb = new StringBuffer();
		byte[] buffer = new byte[DEFAULT_BLOCK_SIZE];
		int read = 0;
		while ( (read = in.read(buffer)) > 0 ){
			sb.append(new String(buffer, 0, read, encoding));
		}
		return sb.toString();
	}
	
}
