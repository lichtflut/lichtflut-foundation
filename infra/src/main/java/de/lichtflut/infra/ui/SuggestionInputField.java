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

import java.awt.Component;

import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


/**
 * Input field for {@link SuggestingComboBox}.
 * 
 * Created: 21.07.2008
 *
 * @author Oliver Tigges
 */
public class SuggestionInputField extends JTextField implements ComboBoxEditor, DocumentListener {
	
	private final TextChangedListener listener;
	
	private Object selected;
	
	//-----------------------------------------------------

	/**
	 * Constructor.
	 */
	public SuggestionInputField(TextChangedListener listener, int columns) {
		this.listener = listener;
		getDocument().addDocumentListener(this);
		setColumns(columns);
		setBorder(new PaddingBorder(0));
	}
	
	//-----------------------------------------------------
	
	public Component getEditorComponent() {
		return this;
	}

	public Object getItem() {
		return selected;
	}

	public void setItem(Object item) {
		if (item != null){
			selected = item;
		}
	}
	
	protected void textChanged() {
		listener.onTextChange(getText());
	}
	
	//-- DocumentListener -----------------------------
	
	public void changedUpdate(DocumentEvent e) {
		textChanged();
	}

	public void insertUpdate(DocumentEvent e) {
		textChanged();
	}

	public void removeUpdate(DocumentEvent e) {
		textChanged();
	}

}
