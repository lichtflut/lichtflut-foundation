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
package de.lichtflut.infra.ui;

import java.util.List;

import de.lichtflut.infra.data.LabeledObject;

/**
 * <p>
 *  Controller interface for {@link SemanticNodeChooser}
 * </p>
 *
 * <p>
 * 	Created Jan 5, 2010
 * </p>
 *
 * @author Oliver Tigges
 */
public interface SuggestionProvider<T> {
	
	/**
	 * Find suggestions for given text.
	 * @param text The text used to search.
	 * @return List of matching objects.
	 */
	List<LabeledObject<T>> getSuggestions(String text);
}
