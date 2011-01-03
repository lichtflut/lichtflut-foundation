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
 * Utility class for Windows' Uniform Naming Convention (UNC).
 * 
 * A UNC address has the following structure: \\ComputerName\SharedFolder\Resource
 * 
 * Created: 17.09.2009
 *
 * @author Oliver Tigges 
 */
public class UNC {
	
	public static final String UNC_PREFIX = "\\\\";
	
	// ------------------------------------------------------
	
	/**
	 * Currently very simple check, if a given path seems to be a UNC.
	 * @param path The path to be checked.
	 * @return true, if given path seems to be an UNC path.
	 */
	public static boolean isUNC(final String path){
		return path.startsWith(UNC_PREFIX);
	}

}