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

/**
 * <p>
 *  The document.
 * </p>
 * 
 * <p>
 * 	Created 24.01.2009
 * </p>
 *
 * @author Oliver Tigges
 */
public class HtmlDocument extends HtmlElement {

	private HtmlHead head;
	private HtmlBody body;
	
	//------------------------------------------------------
	
	public HtmlDocument() {
		super(WellKnownElement.HTML);
	}

	//------------------------------------------------------
	
	public HtmlBody getBody() {
		return body;
	}

	public void setBody(HtmlBody body) {
		this.body = body;
	}
	
	public HtmlHead getHead() {
		return head;
	}
	
	public void setHead(HtmlHead head) {
		this.head = head;
	}
	
	//------------------------------------------------------
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("<" + getName());
		sb.append(" " + getAttributes());
		sb.append(">\n");
		if (head != null){
			sb.append(head.toString(""));
		}
		if (body != null){
			sb.append(body.toString(""));
		}
		sb.append("</" + getName() + ">\n");
		return sb.toString();
	}
	
}
