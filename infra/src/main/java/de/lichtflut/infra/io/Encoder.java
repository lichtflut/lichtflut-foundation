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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;

import de.lichtflut.infra.logging.Log;

/**
 * Encoding util.
 * 
 * Created: 02.12.2008
 *
 * @author Oliver Tigges
 */
public class Encoder {
	
	public static final String UTF_8 = "UTF-8";
	
	public static final String UUML_UC = "\u00DC";
	public static final String UUML_LC = "\u00FC";
	
	public static final String AUML_UC = "\u00C4";
	public static final String AUML_LC = "\u00E4";
	
	public static final String OUML_UC = "\u00D6";
	public static final String OUML_LC = "\u00F6";
	
	//-----------------------------------------------------
	
	public static Reader createFileReader(String filename, String encoding) throws IOException{
		return createFileReader(new File(filename), encoding);
	}
	
	public static Reader createFileReader(File file, String encoding) throws IOException{
		InputStream in = new FileInputStream(file);
		return new InputStreamReader(in, encoding);
	}
	
	public void convert(File file, String fromEncoding, String toEncoding) throws IOException{
		InputStream in = new FileInputStream(file);
		StringWriter cache = new StringWriter();
		Reader reader = new InputStreamReader(in, fromEncoding);
		char[] buffer = new char[128];
		int read;
		while ((read = reader.read(buffer)) > -1){
			cache.write(buffer, 0, read);
		}
		reader.close();
		in.close();
		Log.warn(this, "read from file " + file + " (" + fromEncoding + "):" + cache);
		
		OutputStream out = new FileOutputStream(file);
		OutputStreamWriter writer = new OutputStreamWriter(out, toEncoding);
		writer.write(cache.toString());

		cache.close();
		writer.close();
		out.close();
	}
	
}
