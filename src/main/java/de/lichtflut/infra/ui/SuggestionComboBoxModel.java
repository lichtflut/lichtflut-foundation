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
 * <p>
 * 	Model for {@link SuggestingComboBox}.
 * </p>
 * 
 * <p>
 * 	Created: 21.07.2008
 * </p>
 *
 * @author Oliver Tigges
 */
public class SuggestionComboBoxModel<T> extends AbstractListModel implements ComboBoxModel {

	private final List<LabeledObject<T>> suggestions = new ArrayList<LabeledObject<T>>(20);
	
	private final List<SelectionListener<T>> listeners = new ArrayList<SelectionListener<T>>();
	
	private final String ID; 
	
	private final SuggestionProvider<T> provider;
	
	private int selectionIndex = -1;
	
	//-----------------------------------------------------

	/**
	 * Default constructor.
	 */
	public SuggestionComboBoxModel() {
		this(null);
	}
	
	/**
	 * Constructor.
	 * @param The provider for suggestions.
	 */
	public SuggestionComboBoxModel(final SuggestionProvider<T> provider) {
		this(provider, "<default>");
	}
	
	/**
	 * Constructor.
	 * @param The provider for suggestions.
	 * @param id
	 */
	public SuggestionComboBoxModel(final SuggestionProvider<T> provider, final String id) {
		this.ID = id;
		this.provider = provider;
	}
	
	// -----------------------------------------------------
	
	/**
	 * Remove all suggestions.
	 */
	public void removeSuggestions() {
		this.suggestions.clear();
		super.fireIntervalRemoved(this, 1, suggestions.size());
	}

	/**
	 * Add the given suggestions to the model.
	 * @param suggestions The suggestions.
	 */
	public void addSuggestions(final List<LabeledObject<T>> suggestions) {
		this.suggestions.addAll(suggestions);
		super.fireIntervalAdded(this, 1, suggestions.size());
		setSelectedItem(null);
	}
	
	/**
	 * Add the given suggestions to the model.
	 * @param suggestions The suggestions.
	 */
	public void addSuggestion(final LabeledObject<T> suggestion) {
		this.suggestions.add(suggestion);
		super.fireIntervalAdded(this, 1, 1);
		setSelectedItem(null);
	}
	
	/**
	 * Add the given suggestions to the model.
	 * @param suggestions The suggestions.
	 */
	public void addDefaultSuggestion(final LabeledObject<T> suggestion) {
		this.suggestions.add(suggestion);
		super.fireIntervalAdded(this, 1, 1);
		setSelectedItem(suggestion);
	}
	
	public SuggestionProvider<T> getProvider() {
		return provider;
	}
	
	public T getSelectedSuggestion(){
		final LabeledObject<T> selected = getSelectedItem();
		if (selected != null){
			return selected.getValue();
		} else {
			return null;
		}
	}
	
	//-- ComboBoxModel ------------------------------------
	
	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#getSelectedItem()
	 */
	public LabeledObject<T> getSelectedItem() {
		return getElementAt(selectionIndex);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public LabeledObject<T> getElementAt(int index) {
		if (index < 0 || index >= suggestions.size()){
			return null;
		}
		return suggestions.get(index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return suggestions.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
	 */
	public void setSelectedItem(final Object selected) {
		this.selectionIndex = suggestions.indexOf(selected);
	}
	
	// -----------------------------------------------------

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
	
	public LabeledObject<T> findByLabel(final String input) {
		for (LabeledObject<T> current : suggestions) {
			if (current.equalsLabel(input)){
				return current;
			}
		}
		return null;
	}
	
	// -- LISTENERS -------------------------------
	
	public void addSelectionListener(final SelectionListener<T> listener){
		listeners.add(listener);
	}
	
	protected void fireElementSelected(T value){
		for (SelectionListener<T> current : listeners) {
			 current.elementSelected(value, ID);
		}
	}
	
	protected void fireSelectionFailed(){
		for (SelectionListener<T> current : listeners) {
			 current.selectionFailed(ID);
		}
	}
	
}
