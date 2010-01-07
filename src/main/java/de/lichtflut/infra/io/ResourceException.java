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


/**
 * 
 * Exception for resource operations.
 * 
 * Created: 27.10.2008
 *
 * @author Oliver Tigges
 * 
 */
public class ResourceException extends RuntimeException {

	/**
	 * Constructor with exception only.
	 * @param e The Exception.
	 */
	public ResourceException(Exception e) {
		super(e);
	}

	/**
	 * Constructor with message only.
	 * @param msg The message.
	 */
	public ResourceException(String msg) {
		super(msg);
	}
	
	/**
	 * Constructor with msg and exception.
	 */
	public ResourceException(String msg, Exception e) {
		super(msg, e);
	}

}
