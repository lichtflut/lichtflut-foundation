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

import de.lichtflut.infra.Infra;

/**
 * 
 * Representation of an immutable tuple of two arbitrary values.
 * The values are called "left" and "right" value.
 * 
 * Created: 13.10.2008
 *
 * @author Oliver Tigges
 */
public class Tuple<L, R> {
	
	private final L left;
	private final R right;
	
	//-----------------------------------------------------

	/**
	 * Creates a new immutable tuple with a left and a right value.
	 */
	public Tuple(final L a, final R b) {
		this.left = a;
		this.right = b;
	}
	
	//-----------------------------------------------------

	/**
	 * Returns the left value.
	 */
	public L getLeft() {
		return left;
	}

	/**
	 * Returns the right value.
	 */
	public R getRight() {
		return right;
	}

	//-----------------------------------------------------
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tuple){
			Tuple other = (Tuple) obj;
			return Infra.equals(left, other.left) && Infra.equals(right, other.right);
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return left.hashCode() + right.hashCode();
	}
	
	@Override
	public String toString() {
		return left + "|" + right;
	}
	
}
