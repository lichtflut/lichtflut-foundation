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
/**
 * 
 */
package de.lichtflut.infra.html;

import java.util.HashSet;
import java.util.Set;

/**
 * Well known HTML elements.
 * 
 * Created: 24.01.2009
 * 
 * @author Oliver Tigges
 */
public enum WellKnownElement {
	
	HTML,
	HEAD,
	TITLE,
	META,
	DIV,
	UL,
	IL,
	BODY,
	TABLE,
	TR,
	TD,
	TH,
	P,
	TEXT,
	H1,
	H2,
	H3;
	
	public static WellKnownElement forValue(String value){
		return WellKnownElement.valueOf(value.toUpperCase());
	}
		
	public static boolean isValue(String value){
		return allValues.contains(value.toUpperCase());
	}	
	
	//-----------------------------------------------------
	
	private final static Set<String>  allValues = new HashSet<String> ();
	
	static {
		for (int i = 0; i < WellKnownElement.values().length; i++) {
			WellKnownElement current =  WellKnownElement.values()[i];
			allValues.add(current.name());
		}		
	}

}
