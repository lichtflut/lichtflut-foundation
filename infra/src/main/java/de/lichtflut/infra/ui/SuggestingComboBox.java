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
 * <p>
 * 	Editable select box that contains suggestions based on user input. 
 * </p>
 * 
 * <p>
 *  Created: 18.08.2008
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class SuggestingComboBox<T> extends JComboBox 
		implements TextChangedListener 
	{

	protected final SuggestionInputField editor;
	protected final SuggestionComboBoxModel<T> model;
	protected final SuggestionProvider<T> provider;
	
	private boolean ignoreChanges;
	private boolean resetOnSelection;
	
	//-----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public SuggestingComboBox(final SuggestionComboBoxModel<T> model, int size) {
		this(model, model.getProvider(), size);
	}
	
	/**
	 * Constructor.
	 */
	public SuggestingComboBox(final SuggestionComboBoxModel<T> model, final SuggestionProvider<T> provider, int size) {
		if (provider == null){
			throw new IllegalArgumentException("SuggestionProvider must be given!");
		}
		this.model = model;
		this.provider = provider;
		this.editor = new SuggestionInputField(this, size);
		
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
			model.fireElementSelected(value);
			reset();
		} else {
			model.fireSelectionFailed();
		}
	}
	
	/**
	 * A item in the suggestion box has been selected.
	 * The listeners for this {@link SuggestingComboBox} will be notified.
	 * @param selected The selected item.
	 */
	public void suggestionSelected() {
		LabeledObject<T> wrapper = model.getSelectedItem();
		T value = wrapper.getValue();
		model.fireElementSelected(value);
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
	public void onTextChange(final String text) {
		if (ignoreChanges){
			return;
		}
		ignoreChanges = true;
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final List<LabeledObject<T>> suggestions = provider.getSuggestions(text);
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
		
		private boolean ignoreSelections = false;
		
		// ------------------------------------------------------
		
		/* (non-Javadoc)
		 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyPressed(final KeyEvent e) {
			Log.info(this, "key event: " + e);
			
			ignoreSelections = true;
			
			switch (e.getKeyCode()){
			case KeyEvent.VK_UP:
				if (Platform.isMac()){
					setSelectedIndex(model.selectPrevious());
					ignoreSelections = false;
				}
				break;
			case KeyEvent.VK_DOWN:
				if (Platform.isMac()){
					setSelectedIndex(model.selectNext());
					ignoreSelections = false;
				}
				break;
			case KeyEvent.VK_TAB:
			case KeyEvent.VK_ENTER:
				editor.setFocusTraversalKeysEnabled(true);
				fireSelection();
				break;
			default:
				ignoreSelections = false;
			}
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
		 */
		public void itemStateChanged(final ItemEvent evt) {
			if (ItemEvent.SELECTED == evt.getStateChange()){
				if (!ignoreSelections){
					Log.info(this, "item select event: " + evt);
					fireSelection();
				} else {
					Log.info(this, "item select event ignored: " + evt);
					ignoreSelections = false;
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