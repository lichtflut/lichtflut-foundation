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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Copyright 2008 by Oliver Tigges
 * 
 * @author Oliver Tigges
 * 
 * Created: 11.10.2008
 * 
 * Description: 
 */
public class ProbabilityMatrixPath<K, V> implements Comparable<ProbabilityMatrixPath<K, V>>{

	private final List<Tuple<K, V>> elements = new ArrayList<Tuple<K,V>>();
	private final Set<K> containedKeys = new HashSet<K>();
	private final Set<V> containedValues = new HashSet<V>();
	
	private Probability rating = Probability.MAX; 
	
	//-----------------------------------------------------
	
	public ProbabilityMatrixPath() { }
	
	/**
	 * copy constructor 
	 * @param other
	 */
	protected ProbabilityMatrixPath(ProbabilityMatrixPath<K, V> other) {
		this.elements.addAll(other.elements);
		this.containedValues.addAll(other.containedValues);
		this.containedKeys.addAll(other.containedKeys);
		this.rating = other.rating;
	}
	
	//-----------------------------------------------------
	
	public Probability getProbability() {
		return rating;
	}
	
	public ProbabilityMatrixPath<K, V> copy() {
		return new ProbabilityMatrixPath<K, V>(this);
	}
	
	public void addPathElement(K key, V value, Probability probability){
		elements.add(new Tuple<K, V>(key, value));
		containedValues.add(value);
		containedKeys.add(key);
		rating = rating.weight(probability);
	}
	
	public boolean contains(V value){
		return containedValues.contains(value);
	}
	
	public boolean containsKey(K key){
		return containedKeys.contains(key);
	}
	
	public void checkUsage(Collection<V> values, Probability malus){
		for (V current : values) {
			if (!contains(current)){
				rating = rating.weight(malus);
			}
		}
	}
	
	public void checkKeyUsage(Collection<K> keys, Probability malus){
		for (K current : keys) {
			if (!containsKey(current)){
				rating = rating.weight(malus);
			}
		}
	}
	
	public void invalidate() {
		rating = Probability.MIN;
	}
	
	//-----------------------------------------------------
	
	public int compareTo(ProbabilityMatrixPath<K, V> other) {
		return this.rating.compareTo(other.rating);
	}
	
	@Override
	public String toString() {
		return "PATH{" + elements.size() + "}" + rating;
	}

}
