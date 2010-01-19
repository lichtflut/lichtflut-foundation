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
package de.lichtflut.infra.ui.model;

/**
 * <p>
 *  Request to change data of a model.
 * </p>
 * 
 * <p>
 * 	Created 19.01.2010
 * </p>
 *
 * @author Oliver Tigges
 */
public class ModelChangeRequest<T> {
	
	private final T affectedObject;
	
	private final ModelChangeType type;
	
	private final String target; 
	
	// -----------------------------------------------------
	
	/**
	 * Creates a new request without object.
	 */
	public ModelChangeRequest(final String target, final ModelChangeType type) {
		this(target, type, null);
	}
	
	/**
	 * Creates a new request without object.
	 */
	public ModelChangeRequest(final String target, final ModelChangeType type, final T affectedObject) {
		this.target = target;
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

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}
	

}
