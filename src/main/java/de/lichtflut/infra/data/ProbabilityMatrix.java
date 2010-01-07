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

import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * Copyright 2008 by Oliver Tigges
 * 
 * @author Oliver Tigges
 * 
 * Created: 11.10.2008
 * 
 * Description: 
 */
public class ProbabilityMatrix<K, V> {

	private final MultiMap<K, Probable<V>> map = new MultiMap<K, Probable<V>>();
	
	//------------------------------------------------------
	
	public void addTuple(K key, V value, Probability probability){
		map.add(key, new ProbableObject<V>(probability, value));
	}
	
	//-----------------------------------------------------

	public List<ProbabilityMatrixPath<K, V>> findPaths(){
		List<ProbabilityMatrixPath<K, V>> all = new PathFinder<K, V>(map).findAllPaths();
		Collections.sort(all);
		return all;
	}
	
	public List<ProbabilityMatrixPath<K, V>> findPaths(Collection<K> key, Probability malus){
		List<ProbabilityMatrixPath<K, V>> all = new PathFinder<K, V>(map).findAllPaths();
		for (ProbabilityMatrixPath<K, V> path : all) {
			path.checkKeyUsage(key, malus);
		}
		Collections.sort(all);
		return all;
	}
	
	//-----------------------------------------------------
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("matrix");
		for (K key : map.keySet()) {
			sb.append(key + ":");
			for (Probable<V> value : map.getValues(key)) {
				sb.append(value + "\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
}
