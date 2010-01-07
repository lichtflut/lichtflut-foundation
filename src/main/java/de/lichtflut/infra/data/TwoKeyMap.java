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

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * A special map with two keys.
 * 
 * Created: 28.07.2008
 * 
 * @author Oliver Tigges
 * 
 */
public class TwoKeyMap<K1, K2, V> {
	
	private Map<K1, Map<K2, V>> map = new HashMap<K1, Map<K2, V>>();
	
	//-----------------------------------------------------
	
	/**
	 * Creates a new, empty map.
	 */
	public TwoKeyMap() {}
	
	//-----------------------------------------------------

	/**
	 * Add a new element to a map. The value is mapped by both keys.
	 */
	public void add(K1 key1, K2 key2, V value){
		Map<K2, V> innerMap = getOrCreate(key1);
		innerMap.put(key2, value);
	}
	
	/**
	 * Check if there is a value represented by both keys.
	 * @param key1
	 * @param key2
	 * @return
	 */
	public boolean containsKey(K1 key1, K2 key2){
		Map<K2, V> innerMap = map.get(key1);
		if (innerMap != null){
			return innerMap.containsKey(key2);
		} else {
			return false;
		}
	}
	
	/**
	 * Returns the value represented by both keys.
	 * @param key1
	 * @param key2
	 * @return
	 */
	public V getValue(K1 key1, K2 key2){
		Map<K2, V> innerMap = map.get(key1);
		if (innerMap != null){
			return innerMap.get(key2);
		} else {
			return null;
		}
	}
	
	/**
	 * Clears all keys and values from the map.
	 */
	public void clear() {
		map.clear();
	}
	
	//-----------------------------------------------------
	
	@Override
	public String toString() {
		return map.toString();
	}
	
	//-----------------------------------------------------
	
	protected Map<K2, V> getOrCreate(K1 key1){
		Map<K2, V> innerMap = map.get(key1);
		if (innerMap == null){
			innerMap = new HashMap<K2, V>();
			map.put(key1, innerMap);
		}
		return innerMap;
	}
	
}
