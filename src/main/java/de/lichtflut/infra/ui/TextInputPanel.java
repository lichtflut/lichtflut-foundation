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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;


/**
 * Extended JTextArea panel with history functions.
 *
 * Created: 26.03.2006
 * 
 * @author Oliver Tigges
 */
public class TextInputPanel extends JTextArea implements KeyListener {
	
	public final static int ENTER_KEY = 10;
	public final static int DOWN_KEY = 40;
	public final static int UP_KEY = 38;
	
	private final List<String> history = new ArrayList<String>();
	private final TextChangedListener listener;
	
	private int positionInHistory = 0;
	
	//-----------------------------------------------------

	/**
	 * Constructor.
	 */
	public TextInputPanel(Dimension size, TextChangedListener listener){
		super(size.height, size.width);
		this.listener = listener;
		addKeyListener(this);
		setLineWrap(true);
		setAutoscrolls(true);
		setBackground(Color.WHITE);
		requestFocus();
	}
	
	// ------------------------------------------------------

	public void addToHistory(String text){
		history.add(text);
		positionInHistory = history.size();
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.isControlDown()){
			switch (e.getKeyCode()){
			case KeyEvent.VK_UP:
				if (positionInHistory > 0){
					positionInHistory--;
					setText(history.get(positionInHistory));
				}
				setCaretPosition(0);
				e.consume();
				break;
			case KeyEvent.VK_DOWN:
				if (positionInHistory < (history.size() -1)){
					// check if text changed
					if (getText().equals(history.get(positionInHistory))){
						positionInHistory++;
						setText(history.get(positionInHistory));
					}
				}
				setCaretPosition(0);
				e.consume();
				break;
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.isControlDown()){
			switch (e.getKeyCode()){
			case KeyEvent.VK_ENTER:
				String input = getText();
				setText("");
				listener.onTextChange(input);
				addToHistory(input);
				setCaretPosition(0);
				e.consume();
				break;
			}
		}
	}
	
	public void keyTyped(KeyEvent e) {
		//ignore
	}
}
