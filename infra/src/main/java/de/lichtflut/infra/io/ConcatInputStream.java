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

/**
 * InputStream that concats to streams.
 * 
 * Created: 21.09.2009
 *
 * @author Oliver Tigges 
 */
public class ConcatInputStream extends InputStream {

	private final InputStream is1;
	private final InputStream is2;
	
	private boolean firstfinished = false;

	// ------------------------------------------------------
	
	/**
	 * Creates a new reader based on two readers.
	 */
	public ConcatInputStream(final InputStream is1, final InputStream is2) {
		this.is1 = is1;
		this.is2 = is2;
	}

	// ------------------------------------------------------

	/* (non-Javadoc)
	 * @see java.io.InputStream#read()
	 */
	@Override
	public int read() throws IOException {
		if (!firstfinished){
			int read = is1.read();
			if (read == -1){
				firstfinished = true;
				return read();
			} else {
				return read;
			}
		} else {
			return is2.read();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.io.InputStream#close()
	 */
	@Override
	public void close() throws IOException {
		is1.close();
		is2.close();
	}

}
