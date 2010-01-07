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
package de.lichtflut.infra.exceptions;

/**
 * Exception indicating that an object requested wasn't found.
 * 
 * Created: 11.07.2008 
 *
 *  @author Oliver Tigges
 */
public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2732533474332430981L;
	
	// -----------------------------------------------------
	 
	/**
	 * @param msg
	 */
	public ObjectNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * @param msg
	 */
	public ObjectNotFoundException(Throwable msg) {
		super(msg);
	}

	/**
	 * @param msg
	 * @param exc
	 */
	public ObjectNotFoundException(String msg, Throwable exc) {
		super(msg, exc);
	}
	
}
