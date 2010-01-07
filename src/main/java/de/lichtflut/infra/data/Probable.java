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

/**
 * Represents an Object with a given Probabilty to apply.
 *
 * Created: 28.02.2006
 * 
 * @author Oliver Tigges
 * 
 */
public interface Probable<T> extends Comparable<Probable<T>> {

	/**
	 * Get the value.
	 * @return The value.
	 */
	T getValue();

	/**
	 * Get the probability of this probable value.
	 * @return The {@link Probability}.
	 */
	Probability getProbability();
	
}
