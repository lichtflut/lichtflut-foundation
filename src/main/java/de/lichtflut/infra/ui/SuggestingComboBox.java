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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

import de.lichtflut.infra.Platform;
import de.lichtflut.infra.data.LabeledObject;
import de.lichtflut.infra.logging.Log;

/**
 * Editable select box that contains suggestions based on user input. 
 * 
 * Created: 18.08.2008
 *
 * @author Oliver Tigges
 */
public abstract class SuggestingComboBox<T> extends JComboBox 
		implements TextChangedListener 
	{

	protected final SuggestionInputField editor;
	protected final SuggestionComboBoxModel<T> model;
	protected final SuggestionController<T> controller;
	protected final String ID; 
	
	private boolean ignoreChanges;
	private boolean resetOnSelection;
	
	//-----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public SuggestingComboBox(final SuggestionController<T> controller, int size, String id) {
		this.model = new SuggestionComboBoxModel<T>();
		this.editor = new SuggestionInputField(this, size);
		this.controller = controller;
		this.ID = id;
		
		final SelectObserver observer = new SelectObserver();
		editor.addKeyListener(observer);
		addKeyListener(observer);
		addMouseListener(observer);
		addItemListener(observer);
		
		setEditable(true);
		setEditor(editor);
		setModel(model);
	}

	//-----------------------------------------------------
	
	/**
	 * The current user input in text field has been selected 
	 */
	public void inputSelected(){
		String text = editor.getText();
		LabeledObject<T> wrapper = model.findByLabel(text);
		if (wrapper != null){
			T value = wrapper.getValue();
			fireElementSelected(value);
			reset();
		} else {
			fireSelectionFailed();
		}
	}
	
	/**
	 * A item in the suggestion box has been selected.
	 * The listeners for this {@link SuggestingComboBox} will be notified.
	 * @param selected The selected item.
	 */
	@SuppressWarnings("unchecked")
	public void suggestionSelected() {
		LabeledObject<T> wrapper = (LabeledObject<T>) model.getSelectedItem();
		T value = wrapper.getValue();
		fireElementSelected(value);
		if (isResetOnSelection()){
			reset();
		} else {
			initialize(wrapper.getLabel());
		}
	}
	
	public void reset() {
		initialize("");
	}
	
	public void initialize(final String text){
		ignoreChanges = true;
		editor.setText(text);
		editor.selectAll();
		model.removeSuggestions();
		hidePopup();
		ignoreChanges = false;
	}
	
	public void setText(final String text) {
		editor.setText(text);
	}
	
	public void selectText(){
		editor.selectAll();
	}

	public String getUserInput() {
		return editor.getText();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComboBox#showPopup()
	 */
	@Override
	public void showPopup() {
		if (model.getSize() > 0){
			super.showPopup();
			getUI().setPopupVisible(this, true);
		}
	}
	
	//-- TextChangedListener ------------------------------
	
	/**
	 * called when text in input field is changed.
	 */
	public void onTextChange(String text) {
		if (ignoreChanges){
			return;
		}
		ignoreChanges = true;
		final List<LabeledObject<T>> suggestions = controller.getSuggestions(text);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				model.removeSuggestions();
				model.addSuggestions(suggestions);
				updateFinished();
			}
		});
	}
	
	// ------------------------------------------------------
	
	protected void updateFinished() {
		ignoreChanges = false;
		if (isShowing()){
			showPopup();
			editor.setFocusTraversalKeysEnabled(false);
		}
	}
	
	protected void fireElementSelected(T value){
		if (controller != null){
			controller.elementSelected(value, ID);
		}
	}
	
	protected void fireSelectionFailed(){
		if (controller != null){
			controller.selectionFailed(ID);
		}
	}

	public void setResetOnSelection(boolean resetOnSelection) {
		this.resetOnSelection = resetOnSelection;
	}

	public boolean isResetOnSelection() {
		return resetOnSelection;
	}
	
	// ------------------------------------------------------
	
	/**
	 * Inner class. Observer for key events indicating, that the user selected
	 * the current suggestion.
	 */
	private class SelectObserver extends KeyAdapter implements ItemListener, MouseListener {
		
		private boolean ignoreEvents = false;
		
		// ------------------------------------------------------
		
		/* (non-Javadoc)
		 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyPressed(final KeyEvent e) {
			Log.info(this, "key event: " + e);
			ignoreEvents = true;
			switch (e.getKeyCode()){
			case KeyEvent.VK_UP:
				if (Platform.isMac()){
					setSelectedIndex(model.selectPrevious());
				}
				break;
			case KeyEvent.VK_DOWN:
				if (Platform.isMac()){
					setSelectedIndex(model.selectNext());
				}
				break;
			case KeyEvent.VK_TAB:
			case KeyEvent.VK_ENTER:
				editor.setFocusTraversalKeysEnabled(true);
				fireSelection();
				break;
			}
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
		 */
		public void itemStateChanged(final ItemEvent evt) {
			Log.info(this, "item event: " + evt);
			if (ItemEvent.SELECTED == evt.getStateChange()){
				if (!ignoreEvents){
					fireSelection();
				} else {
					ignoreEvents = false;
				}
			}
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(final MouseEvent evt) {
			Log.info(this, "mouse event: " + evt);
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		public void mouseEntered(final MouseEvent evt) {
			// ignore
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		public void mouseExited(final MouseEvent evt) {
			// ignore
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		public void mousePressed(final MouseEvent evt) {
			Log.info(this, "mouse event: " + evt);
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		public void mouseReleased(final MouseEvent evt) {
			Log.info(this, "mouse event: " + evt);
		}
		
		// ------------------------------------------------------
		
		private void fireSelection() {
			if (getSelectedIndex() >= 0){
				suggestionSelected();
			} else {
				inputSelected();
			}
		}

	}
	
}