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
package de.lichtflut.infra;

/**
 * Plattform specifics.
 * 
 * Created: 10.07.2009
 *
 * @author Oliver Tigges
 */
public class Platform {
	
	static enum OperatingSystem {
		MAC_OS_X,
		WINDOWS,
		OTHER;
	}
	
	// -----------------------------------------------------
	
	/**
	 * Checks if current platform is Mac OS X.
	 * @return
	 */
	public static boolean isMac(){
		return OperatingSystem.MAC_OS_X.equals(getOperatingSystem());
	}
	
	/**
	 * Checks the operating system.
	 * @return The enum for the operating system.
	 */
	public static OperatingSystem getOperatingSystem(){
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.startsWith("mac os x")){
			return OperatingSystem.MAC_OS_X;
		} else if (osName.startsWith("windows")){
			return OperatingSystem.WINDOWS;
		} else {
			return OperatingSystem.OTHER;
		}
	}

}