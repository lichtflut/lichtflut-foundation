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
import java.io.OutputStream;

/**
 * Streams data between in- and output streams.
 * 
 * Created: 15.12.2008
 *
 * @author Oliver Tigges
 */
public class Streamer {
	
	private final int bufferSize;
	
	// ------------------------------------------------------
	
	/**
	 * Creates a new {@link Streamer}.
	 */
	public Streamer() {
		this(2000);
	}
	
	
	/**
	 * Creates a new {@link Streamer} with a configured buffer size.
	 * @param bufferSize The size of the buffer to be used.
	 */
	public Streamer(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	// ------------------------------------------------------

	/**
	 * Streams the input stream to the output stream.
	 */
	public void stream(final InputStream in, final OutputStream out) throws IOException{
		byte buffer[] = new byte[bufferSize];
		int read = in.read(buffer);
		while (read > -1){
			out.write(buffer, 0, read);
			read = in.read(buffer);
		}
	}
	
}
