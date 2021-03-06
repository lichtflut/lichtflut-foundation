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
package de.lichtflut.infra.logging;

import org.slf4j.LoggerFactory;


/**
 * <p>
 * 	Simple utility class for static logging using SL4J.
 * </p>
 * 
 * <p>
 * 	Created: 12.08.2004 
 * </p>
 * 
 * @author Oliver Tigges
 */
public class Log {
	
	public static void debug(Object caller, String msg){
		LoggerFactory.getLogger(getClassname(caller)).debug(msg);
	}
	
	public static void info(Object caller, String msg){
		LoggerFactory.getLogger(getClassname(caller)).info(msg);
	}
	
	public static void info(String logger, String msg){
		LoggerFactory.getLogger(logger).info(msg.toString());
	}
	
	public static void warn(Object caller, String msg){
		LoggerFactory.getLogger(getClassname(caller)).warn(msg);
	}
	
	public static void error(Object caller, String msg){
		LoggerFactory.getLogger(getClassname(caller)).error(msg);
	}
	
	public static void exception(Object caller, Throwable e, String msg){
		LoggerFactory.getLogger(getClassname(caller)).error(msg, e);
	}
	
	public static void currentStack(Object caller){
		LoggerFactory.getLogger(getClassname(caller)).error("current stack: ", new RuntimeException());
	}
	
	//-----------------------------------------------------
	
	@SuppressWarnings("rawtypes")
	private static String getClassname(Object caller){
		Class callerClass = null;
		if (caller instanceof Class){
			callerClass = (Class) caller;
		} else {
			callerClass = caller.getClass();
		} 
		if (callerClass != null){
			return callerClass.getCanonicalName();
		} else {
			return "NO_CLASS";
		}
	}
	
}