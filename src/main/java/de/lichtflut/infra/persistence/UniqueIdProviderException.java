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
package de.lichtflut.infra.persistence;

/**
 * 
 * Copyright 2005 by Oliver Tigges
 *
 * @author Oliver Tigges
 *
 * Created: 15.05.2005
 * Description: Exception is thrown when errors occur while looking up Unique IDs
 */
public class UniqueIdProviderException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2755150531963305791L;

	/**
	 * @param cause
	 */
	public UniqueIdProviderException(Throwable cause) {
		super("Error while trying to reserve block of unique IDs", cause);
	}
	
	
}
