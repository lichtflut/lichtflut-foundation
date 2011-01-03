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
/**
 * 
 */
package de.lichtflut.infra.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base for all HTML elements.
 * 
 * Created: 24.01.2009
 * 
 * @author Oliver Tigges
 */
public abstract class HtmlElement {
	
	private final String name;
	
	private Map<String, String> attributes = new HashMap<String, String>();
	
	private List<HtmlElement> children = new ArrayList<HtmlElement>();
	
	private String content, text;
	
	private HtmlElement parent;

	//------------------------------------------------------
	
	public HtmlElement(String name) {
		this.name = name;
	}
	
	public HtmlElement(WellKnownElement wke) {
		this.name = wke.name();
	}
	
	//------------------------------------------------------
	
	
	public HtmlElement getParent(){
		return parent;
	}
	
	public void setParent(HtmlElement parent){
		this.parent = parent;
	}
	
	public String getName() {
		return name;
	}
	
	public String getText(){
		return text;
	}
	
	public List<HtmlElement> getChildren() {
		return children;
	}
	
	public String getContent() {
		return content;
	}
	
	//------------------------------------------------------
	
	public void setText(String text){
		this.text = text;
	}
	
	public void addChild(HtmlElement child) {
		children.add(child);
	}
	
	public void addAttribute(String name, String value){
		attributes.put(name, value);
	}
	
	public Map<String, String> getAttributes() {
		return attributes;
	}
	
	//------------------------------------------------------
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("<" + getName());
		sb.append(" " + attributes);
		sb.append(">");
		return sb.toString();
	}
	
	public String toString(String indent) {
		StringBuffer sb = new StringBuffer(indent + "<" + getName());
		sb.append(" " + attributes);
		sb.append(">\n");
		for (HtmlElement child : children) {
			sb.append(child.toString(indent + "\t"));
		}
		sb.append(indent +"</" + getName() + ">\n");
		return sb.toString();
	}

	

}
