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
 *  Base interface for models where observers can be registered to detect changes.
 * </p>
 * 
 * <p>
 * 	Created 19.01.2010
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ObservableModel {

	/**
	 * Registers an observer for given change type.
	 */
	void registerObserver(final String source, final ModelObserver<?> observer);

	/**
	 * Unregisters an observer.
	 */
	void unregisterObserver(final String source, final ModelObserver<?> observer);
	
}
