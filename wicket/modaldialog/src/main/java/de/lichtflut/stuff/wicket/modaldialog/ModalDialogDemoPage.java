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
package de.lichtflut.stuff.wicket.modaldialog;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;


public class ModalDialogDemoPage extends WebPage {
	private static final long serialVersionUID = 1L;

    @SuppressWarnings({ "rawtypes", "serial" })
	public ModalDialogDemoPage(final PageParameters parameters) {
    	Form form = new Form("form");
    	add(form);
    	
		final ModalDialog modalDialog = new ModalDialog("modalDialog",false);
		modalDialog.setOutputMarkupId(true);
		modalDialog.displayCloseLink(true);
		modalDialog.setTitle(new Model<String>("ModalDialogTitle"));
		modalDialog.setContent( new Label( "content",new Model<String>( "Hello World" ) ) );
		modalDialog.setVisible(false);
		form.add(modalDialog);
		
		form.add(new AjaxButton("openModalDialogButton", new Model<String>("open modal dialog")) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				modalDialog.setVisible(true);
				target.addComponent(modalDialog);
			}
		});
    }
}
