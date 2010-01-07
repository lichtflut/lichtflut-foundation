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
package de.lichtflut.infra.html;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 * 
 * <p>
 * 	Created 07.08.2009
 * </p>
 *
 * @author Nils Bleisch
 */
public final class HtmlFilter {
	
	private Map<WellKnownElement,WellKnownElement> filterRules = new HashMap<WellKnownElement,WellKnownElement>();
	
	// -----------------------------------------------------
	
	public void addFilterRule(WellKnownElement rule){
		if(!isFilterRule(rule))
			filterRules.put(rule, rule);
	}//End of Method addFilterRule()
	
	public void setFilterRules(Map<WellKnownElement,WellKnownElement> filterRules){
		reset(filterRules);
	}//End of Method setFilterRules
	
	public Map<WellKnownElement,WellKnownElement> getFilterRules(){
		return filterRules;
	}//End of Method getFilterRules
	
	public void removeFilterRule(WellKnownElement rule){
		filterRules.remove(rule);
	}//End of Method removeFilterRule
	
	public boolean isFilterRule(WellKnownElement elem){
		return filterRules.containsKey(elem);
	}//End of Method isFilterRule()
	
	public void reset(){
		filterRules = new HashMap<WellKnownElement,WellKnownElement>();
	}//End of Method reset()
	
	public void reset(Map<WellKnownElement,WellKnownElement> filterRules){
		reset();
		this.filterRules.putAll(filterRules);
	}//End of Method reset()
	
	//Invertiert die Filterregeln auf Basis der WellKnownElement
	public void invert(){
		for(WellKnownElement elem: WellKnownElement.values()){
			if(!isFilterRule(elem))
				filterRules.put(elem,elem);
			else removeFilterRule(elem);
		}
	}//end of Method invert()
	
}//end of class HtmlFilter
