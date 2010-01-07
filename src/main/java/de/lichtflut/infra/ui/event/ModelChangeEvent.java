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
package de.lichtflut.infra.ui.event;

/**
 * <p>
 *  Events for model changes.
 * </p>
 *
 * <p>
 * 	Created Jan 6, 2010
 * </p>
 *
 * @author Oliver Tigges
 */
public class ModelChangeEvent<T> {
	
	private final T affectedObject;
	
	private final ModelChangeType type;
	
	private final String source; 

	// -----------------------------------------------------
	
	/**
	 * Creates a new default event.
	 */
	public ModelChangeEvent(final String source) {
		this(source, null, ModelChangeType.UNDEFINED);
	}
	
	/**
	 * Creates a new event.
	 */
	public ModelChangeEvent(final String source, final T affectedObject, final ModelChangeType type) {
		this.source = source;
		this.affectedObject = affectedObject;
		this.type = type;
	}
	
	// -----------------------------------------------------
	
	/**
	 * @return the affectedObject
	 */
	public T getAffectedObject() {
		return affectedObject;
	}

	/**
	 * @return the type
	 */
	public ModelChangeType getType() {
		return type;
	}
	
	public String getSource() {
		return source;
	}

}
