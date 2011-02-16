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
