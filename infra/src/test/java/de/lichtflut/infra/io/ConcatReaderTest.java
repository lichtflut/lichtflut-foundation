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
import java.io.StringReader;

import junit.framework.TestCase;

/**
 * Test case for {@link ConcatReader}.
 * 
 * Created: 21.09.2009
 *
 * @author Oliver Tigges 
 */
public class ConcatReaderTest extends TestCase {
	
	public void testConcat() throws IOException{
		ConcatReader cr = new ConcatReader(new StringReader("hallo "), new StringReader("bill"));
		
		char[] chars = new char[100];
		
		final StringBuffer sb = new StringBuffer();
		int read = cr.read(chars);
		while (read > -1){
			sb.append(chars, 0, read);
			read = cr.read(chars);
		}
		assertEquals("hallo bill", sb.toString());
	}

}
