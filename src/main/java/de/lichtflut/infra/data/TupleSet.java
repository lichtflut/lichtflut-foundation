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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Container for {@link Tuple}s. These have a "left" and a "right" value.
 * 
 * Created: 22.10.2008
 *
 * @author Oliver Tigges
 */
public class TupleSet<L,R> {
	
	private final Map<L,Tuple<L,R>> leftMap = new HashMap<L, Tuple<L,R>>();
	private final Map<R,Tuple<L,R>> rightMap = new HashMap<R, Tuple<L,R>>();
	
	//-----------------------------------------------------
	
	/**
	 * Add two values, that build a tuple.
	 */
	public void add(L left, R right){
		add(new Tuple<L, R>(left, right));
	}
	
	/**
	 * Add a tuple.
	 * @param tuple The tuple.
	 */
	public void add(final Tuple<L,R> tuple){
		leftMap.put(tuple.getLeft(), tuple);
		rightMap.put(tuple.getRight(), tuple);
	}
	
	/**
	 * Add many tuples.
	 * @param all A collection containing tuples.
	 */
	public void addAll(final Collection<Tuple<L, R>> all){
		for (Tuple<L,R> current : all) {
			add(current);
		}
	}
	
	//-----------------------------------------------------
	
	public boolean containsLeft(L value){
		return leftMap.containsKey(value);
	}
	
	public boolean containsRight(R value){
		return rightMap.containsKey(value);
	}
	
	//-----------------------------------------------------
	
	public Set<L> getLefts(){
		return Collections.unmodifiableSet(leftMap.keySet());
	}
	
	public Set<R> getRights(){
		return Collections.unmodifiableSet(rightMap.keySet());
	}
	
	/**
	 * Get the corresponding right value for given left value.
	 * @param left The left value of the tuple.
	 * @return The corresponding right value.
	 */
	public R getRight(L left) {
		return leftMap.get(left).getRight();
	}
	
	/**
	 * Get the corresponding left value for given right value.
	 * @param right The right value of the tuple.
	 * @return The corresponding left value.
	 */
	public L getLefts(R right) {
		return rightMap.get(right).getLeft();
	}
	
	public Tuple<L, R> getTuple(Object key){
		if (leftMap.containsKey(key)){
			return leftMap.get(key);
		} else {
			return rightMap.get(key);
		}
	}
	
	public Collection<Tuple<L, R>> getTuples() {
		return leftMap.values();
	}

	//-----------------------------------------------------
	
	public void remove(Tuple<L, R> tuple) {
		leftMap.remove(tuple.getLeft());
		rightMap.remove(tuple.getRight());
	}
	
	//-----------------------------------------------------
	
	@Override
	public String toString() {
		return leftMap.values().toString();
	}

}
