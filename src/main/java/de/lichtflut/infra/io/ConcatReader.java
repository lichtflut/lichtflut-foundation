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
import java.io.Reader;

/**
 * Reader that concats to readers.
 * 
 * Created: 21.09.2009
 *
 * @author Oliver Tigges 
 */
public class ConcatReader extends Reader {

	private final Reader r1;
	private final Reader r2;

	// ------------------------------------------------------
	
	/**
	 * Creates a new reader based on two readers.
	 */
	public ConcatReader(final Reader r1, final Reader r2) {
		this.r1 = r1;
		this.r2 = r2;
	}
	
	// ------------------------------------------------------

	/* (non-Javadoc)
	 * @see java.io.Reader#close()
	 */
	@Override
	public void close() throws IOException {
		r1.close();
		r2.close();
	}

	/* (non-Javadoc)
	 * @see java.io.Reader#read(char[], int, int)
	 */
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		int read = r1.read(cbuf, off, len);
		if (read == -1){
			read = r2.read(cbuf, off, len);
		}
		return read;
	}

}
