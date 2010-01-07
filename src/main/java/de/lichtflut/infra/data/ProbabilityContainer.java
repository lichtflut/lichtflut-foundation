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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Container for objects/statements/issues with
 * their probability to apply.
 * 
 * Created: 12.11.2005 
 * 
 * @author Oliver Tigges
 */
public class ProbabilityContainer<T> implements Iterable<T>{

	private List<Probable<T>> entries = new ArrayList<Probable<T>>();
	
	// ------------------------------------------------------

	public ProbabilityContainer() { }
	
	// -----------------------------------------------------

	/**
	 * Return the Object with the highest Probability
	 * 
	 * @return Object
	 */
	public T getMostLikely() {
		return getMostLikely(Probability.MIN);
	}

	/**
	 * Return the Object with the highest Probability when probability is of
	 * given minumium
	 * 
	 * @return Object
	 */
	public T getMostLikely(Probability minimum) {
		if (entries.isEmpty())
			return null;
		Collections.sort(entries);
		Probable<T> entry = entries.get(0);
		if (entry.getProbability().greaterEquals(minimum))
			return entry.getValue();
		else
			return null;
	}
	
	/**
	 * Returns all Objects sorted by their probability
	 */
	public Collection<T> getAll() {
		return getAll(Probability.MIN); 
	}
	
	/**
	 * Returns all Objects sorted by their probability over given minimum
	 */
	public Collection<T> getAll(Probability minimum) {
		List<T> result = new ArrayList<T>(entries.size());
		Collections.sort(entries);
		for (Iterator<Probable<T>> iter = entries.iterator(); iter.hasNext();) {
			Probable<T> element = iter.next();
			if (element.getProbability().greaterEquals(minimum))
				result.add( element.getValue());
		}
		return result;
	}
	
	//-----------------------------------------------------
	
	/**
	 * Add all {@link Probable}s from collection
	 * 
	 * @param o
	 */
	public void addAll(Collection<Probable<T>> collection) {
		entries.addAll(collection);
	}

	/**
	 * Add {@link Probable}
	 * 
	 * @param o
	 */
	public void add(Probable<T> p) {
		entries.add(p);
	}

	/**
	 * Add a new Object with its probability
	 * 
	 * @param o
	 * @param propability
	 */
	public void add(T o, Probability propability) {
		entries.add(new ProbableObject<T>(propability, o));
	}
	
	/**
	 * @param toBeRemoved
	 * @return true if element was found and removed
	 */
	public boolean remove(Probable<T> toBeRemoved){
		return entries.remove(toBeRemoved);
	}
	
	public void clear(){
		entries.clear();
	}
	
	//-----------------------------------------------------
	
	public int size(){
		return entries.size();
	}

	public boolean isEmpty() {
		return entries.isEmpty();
	}
	
	/**
	 * Removes all entries that have less than half probability than best
	 */
	public void removeWorst(Probability factor){
		if (entries.size() < 2){
			return;
		}
		Set<Probable<T>> toBeRemoved = new HashSet<Probable<T>>();
		double limit = getBest() * factor.getDouble();
		for (Probable<T> current : entries) {
			if (current.getProbability().getDouble() < limit){
				toBeRemoved.add(current);
			}
		}
		entries.removeAll(toBeRemoved);
	}
	
	public Iterator<T> iterator() {
		return new Iterator<T>(){
			public boolean hasNext() {
				return index < entries.size();
			}
			
			public T next() {
				return entries.get(index++).getValue();
			}
			
			public void remove() {
				entries.remove(index);
			}
			private int index;
		};
	}
	
	public String toString() {
		Collections.sort(entries);
		StringBuffer sb = new StringBuffer();
		for (Iterator<Probable<T>> iter = entries.iterator(); iter.hasNext();) {
			Probable<T> entry = iter.next();
			sb.append(entry.getValue());
			sb.append("(" + entry.getProbability() + ")");
			if (iter.hasNext())
				sb.append(", ");
		}
		return sb.toString();
	}
	
	// ------------------------------------------------------

	protected double getBest(){
		double best = 0.0d;
		for (Probable<T> probable : entries) {
			if (probable.getProbability().getDouble() > best)
				best = probable.getProbability().getDouble();
		}
		return best;
	}

}
