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
package de.lichtflut.infra.html.provider;

import java.util.List;


import org.jdom.Attribute;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Text;

import de.lichtflut.infra.html.DefaultHtmlElement;
import de.lichtflut.infra.html.HtmlBody;
import de.lichtflut.infra.html.HtmlDocument;
import de.lichtflut.infra.html.HtmlElement;
import de.lichtflut.infra.html.HtmlHead;
import de.lichtflut.infra.html.HtmlTable;
import de.lichtflut.infra.html.HtmlText;
import de.lichtflut.infra.html.WellKnownElement;
import de.lichtflut.infra.logging.Log;

/**
 * Provides {@link HtmlDocument} from XHTML.
 * 
 * Created: 24.01.2009
 * 
 * @author Oliver Tigges
 */
public class XhtmlProvider {
	
	@SuppressWarnings("unchecked")
	public HtmlDocument create(Document xmlDoc){
		HtmlDocument htmlDoc = new HtmlDocument();
		Element root = xmlDoc.getRootElement();
		List<Element> children = root.getChildren();
		for (Element child : children) {
			HtmlElement childHtml = toHtmlRecursive(child);
			if (isHtmlBody(childHtml)){
				htmlDoc.setBody((HtmlBody) childHtml);
			} else if (isHtmlHead(childHtml)){
				htmlDoc.setHead((HtmlHead) childHtml);
			} else {
				Log.debug(this, " no use for " + childHtml);
			}
			htmlDoc.addChild(childHtml);
		}
		return htmlDoc;
	}
	
	//------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	protected HtmlElement toHtmlRecursive(Element xmlElement){
		HtmlElement html = toHtml(xmlElement);
		List<Content> contents = xmlElement.getContent();
		for (Content node : contents) {
			HtmlElement childHtml;
			if(node instanceof Text){
				childHtml = new HtmlText(WellKnownElement.TEXT);
				childHtml.setText(((Text)node).getText());
				((HtmlText)childHtml).setNormalizedText(((Text)node).getTextNormalize());
				childHtml.setParent(html);
				html.addChild(childHtml);
			}else if(node instanceof Element){
				childHtml = toHtmlRecursive((Element)node);
				childHtml.setParent(html);
				html.addChild(childHtml);
			}
	
		}
		return html;
	}
	
	@SuppressWarnings("unchecked")
	protected HtmlElement toHtml(Element xmlElement){
		HtmlElement html = createHtmlElement(xmlElement);
		List<Attribute> attributes = xmlElement.getAttributes();
		for (Attribute attribute : attributes) {
			html.addAttribute(attribute.getName(), attribute.getValue());
		}
		return html;
	}
	
	protected HtmlElement createHtmlElement(Element xmlElement){
		String name = xmlElement.getName();
		HtmlElement elem = new DefaultHtmlElement("default");
		if (WellKnownElement.isValue(name)){
			WellKnownElement wke = WellKnownElement.forValue(name);
			switch(wke){
			case BODY:
				elem = new HtmlBody(); break;
			case HEAD:
				elem =  new HtmlHead(); break;
			case TABLE:
				elem  =  new HtmlTable(); break;
			default: elem = new DefaultHtmlElement(xmlElement.getName());
			}//end of if
		}
		elem.setText(xmlElement.getText());
		return elem; 
	}
	
	//------------------------------------------------------
	
	private boolean isHtmlBody(HtmlElement childHtml) {
		return childHtml instanceof HtmlBody;
	}
	
	private boolean isHtmlHead(HtmlElement childHtml) {
		return childHtml instanceof HtmlHead;
	}

}
