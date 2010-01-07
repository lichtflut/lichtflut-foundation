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

import java.util.Collection;

import javax.swing.DefaultComboBoxModel;

import de.lichtflut.infra.data.LabeledObject;

/**
 * [TODO Insert description here.]
 * 
 * Created: 21.07.2009
 *
 * @author Oliver Tigges 
 */
public class LabeledComboBoxModel<T> extends DefaultComboBoxModel {

	/**
	 * 
	 */
	public LabeledComboBoxModel() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param items
	 */
	public LabeledComboBoxModel(final LabeledObject<T>[] items) {
		super(items);
	}

	/**
	 * @param items
	 */
	public LabeledComboBoxModel(final Collection<LabeledObject<T>> items) {
		super(items.toArray());
	}

}
