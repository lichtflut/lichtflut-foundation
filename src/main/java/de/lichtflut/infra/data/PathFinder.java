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
package de.lichtflut.infra.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Finds a path in a {@link ProbabilityMatrix}.
 * 
 * Created: 11.10.2008
 * 
 * @author Oliver Tigges
 */
public class PathFinder<K, V> {
	
	private final List<ProbabilityMatrixPath<K,V>> paths = new ArrayList<ProbabilityMatrixPath<K,V>>();
	private final MultiMap<K, Probable<V>> map;
	
	//------------------------------------------------------
	
	public PathFinder(MultiMap<K, Probable<V>> map) {
		this.map = map;
	}
	
	//-----------------------------------------------------

	/**
	 * Find all paths in the matrix
	 */
	public List<ProbabilityMatrixPath<K,V>> findAllPaths(){
		paths.add(new ProbabilityMatrixPath<K,V>());
		Set<K> keys = map.keySet();
		for (K key : keys) {
			append(key);
		}
		return paths;
	}

	//------------------------------------------------------
	
	/**
	 * append the node for given key to result list
	 * @param key
	 * @param paths
	 * @return
	 */
	private void append(K key) {
		Set<Probable<V>> possibles = map.getValues(key);
		if (possibles.isEmpty()){
			// no value for key
		} else if (possibles.size() == 1){
			// exactly one value for key
			Probable<V> pv = possibles.iterator().next();
			// appending this element to all paths
			appendToPaths(key, pv, paths);
		} else {
			// many values for keys --> lists have to be forked
			int size = possibles.size();
			List<ProbabilityMatrixPath<K,V>> origs = new ArrayList<ProbabilityMatrixPath<K,V>>(paths);
			// iterate over copy, because original will be modified
			for (ProbabilityMatrixPath<K,V> current : origs) {
				List<ProbabilityMatrixPath<K,V>> altPahts = cloneToSize(current, size);
				Iterator<Probable<V>> valueIterator = possibles.iterator();
				for (int i=0; i < size; i++){
					Probable<V> pv = valueIterator.next();
					ProbabilityMatrixPath<K,V> path = altPahts.get(i);
					if (!path.contains(pv.getValue())){
						path.addPathElement(key, pv.getValue(), pv.getProbability());
					} else {
						path.invalidate();
					}
				}
			}
		}
	}

	private void appendToPaths(K key, Probable<V> pv, List<ProbabilityMatrixPath<K,V>> paths) {
		for (ProbabilityMatrixPath<K,V> path : paths) {
			if (!path.contains(pv.getValue())){
				path.addPathElement(key, pv.getValue(), pv.getProbability());
			} else {
				path.invalidate();
			}
		}
	}
	
	/**
	 * clones the given path (size -1) times to have "size" instances 
	 * @param original
	 * @param size
	 * @return
	 */
	private List<ProbabilityMatrixPath<K,V>> cloneToSize(ProbabilityMatrixPath<K,V> original, int size){
		List<ProbabilityMatrixPath<K,V>> result = new ArrayList<ProbabilityMatrixPath<K,V>>(size);
		result.add(original);
		for (int i = 1; i <= size -1; i++){
			ProbabilityMatrixPath<K,V> clone = original.copy();
			paths.add(clone);
			result.add(clone);
		}
		return result;
	}

}
