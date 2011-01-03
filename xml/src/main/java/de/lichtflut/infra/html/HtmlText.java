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
package de.lichtflut.infra.html;

/**
 * <p>
 *  This class is representing the Text.
 * </p>
 * 
 * <p>
 * 	Created 07.07.2009
 * </p>
 *
 * @author Nils Bleisch
 */
public class HtmlText extends HtmlElement {

	//Member-Field
	private String text, textNormalized;
	


	public HtmlText(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public HtmlText(WellKnownElement wke) {
		super(wke);
		// TODO Auto-generated constructor stub
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public String getNormalizedText(){
		return textNormalized;
	}
	
	public void setNormalizedText(String textNormalized) {
		this.textNormalized = textNormalized;
		
	}
	

}
