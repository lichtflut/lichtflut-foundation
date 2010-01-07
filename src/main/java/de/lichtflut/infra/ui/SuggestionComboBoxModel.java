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

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import de.lichtflut.infra.data.LabeledObject;

/**
 * Model for {@link SuggestingComboBox}.
 * 
 * Created: 21.07.2008
 *
 * @author Oliver Tigges
 */
public class SuggestionComboBoxModel<T> extends AbstractListModel implements ComboBoxModel {

	private final List<LabeledObject<T>> suggestions = new ArrayList<LabeledObject<T>>(20);
	
	private int selectionIndex = -1;
	
	//-----------------------------------------------------
	
	public void removeSuggestions() {
		this.suggestions.clear();
		super.fireIntervalRemoved(this, 1, suggestions.size());
	}

	public void addSuggestions(final List<LabeledObject<T>> suggestions) {
		this.suggestions.addAll(suggestions);
		super.fireIntervalAdded(this, 1, suggestions.size());
		setSelectedItem(null);
	}
	
	//-----------------------------------------------------
	
	public Object getSelectedItem() {
		return getElementAt(selectionIndex);
	}
	
	public int selectNext(){
		if (selectionIndex < (suggestions.size()-1)){
			selectionIndex++;
		}
		return selectionIndex;
	}
	
	public int selectPrevious(){
		if (selectionIndex > 0){
			selectionIndex--;
		} else {
			selectionIndex = getSize() -1;
		}
		return selectionIndex;
	}
	
	public void setSelectedItem(Object selected) {
		this.selectionIndex = suggestions.indexOf(selected);
	}

	public Object getElementAt(int index) {
		if (index < 0 || index >= suggestions.size()){
			return null;
		}
		return suggestions.get(index);
	}

	public int getSize() {
		return suggestions.size();
	}
	
	public LabeledObject<T> findByLabel(final String input) {
		for (LabeledObject<T> current : suggestions) {
			if (current.equalsLabel(input)){
				return current;
			}
		}
		return null;
	}
	
}
