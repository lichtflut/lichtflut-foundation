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
package de.lichtflut.infra.data;

import java.util.HashMap;

/**
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 * 
 * @author Oliver Tigges
 * 
 * Created: 13.03.2009
 * 
 * Description: 
 */
public class ProbabilityMap<T> extends HashMap<T, Probability>{

	public ProbabilityMap() {}
	
	// -----------------------------------------------------
	
	public Probability getProbability(T key){
		return super.get(key);
	}
	
	public void putWhenGreater(T key, Probability p){
		Probability current = super.get(key);
		if (current == null || p.greater(current)){
			super.put(key, p);
		}
	}

}
