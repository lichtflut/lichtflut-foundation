package de.lichtflut.stuff.wicket.modaldialog;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.lichtflut.stuff.wicket.modaldialog.resources.CommonResources;

/**
 * Ein modaler Dialog, der ��ber eine Seite gelegt wird und diese sperrt bis der Dialog geschlossen wird.
 * 
 * @author Oliver Tigges
 */
public class ModalDialog extends Panel {
	
	private static final long serialVersionUID = 1L;

	private static final String CONTENT_ID = "content";
	
	private final Label titleLabel = new Label("title", Model.of(""));

	private final WebMarkupContainer container = new WebMarkupContainer("container");;
	
    /**
     * Konstruktor. Erzeugt und initialisiert das Content Panel.
     * Der Dialog wird noch nicht angezeigt.
     * @param componentID Die component ID des content panels.
     */
    public ModalDialog(final String componentID) {
        this(componentID, false);
    }
    
    /**
     * Konstruktor. Erzeugt und initialisiert das Content Panel.
     * @param componentID Die component ID des content panels.
     * @param show Gibt an ob der Dialog direkt gezeigt werden soll.
     */
    public ModalDialog(final String componentID, final boolean show) {
        this(componentID, null, show);
    }
    
    /**
     * Konstruktor. Erzeugt und initialisiert das Content Panel mit einem Model.
     * Sofern das Model nciht schon ein {@link TransactionalModel} ist, wird es entsprechend
     * verpackt. In diesem Fall muss sp�ter mit dem Model weitergearbeitet werden,
     * welches <code>getDefaultModel()</code> liefert.
     * Der Dialog wird noch nicht angezeigt.
     * @param componentID Die component ID des content panels.
     * @param model Das Modell des Dialogs.
     */
    public ModalDialog(final String componentID, final IModel<?> model) {
        this(componentID, model,  false);
    }
    
    /**
     * Konstruktor. Erzeugt und initialisiert das Content Panel mit einem Model.
     * @param componentID Die component ID des content panels.
     * @param model Das Modell des Dialogs.
     * @param show Gibt an ob der Dialog direkt gezeigt werden soll.
     */
    @SuppressWarnings({ "rawtypes", "serial" })
    public ModalDialog(final String componentID,  final IModel<?> model, final boolean show) {
        super(componentID, model);

        setVisible(show);
        
        setOutputMarkupPlaceholderTag(true);
        
        add(container);
        
        container.setOutputMarkupId(true);
        titleLabel.setOutputMarkupId(true);
        
        // IFrame als z-Index-Fix f�r IE6 ohne JavaScript
        container.add(new WebMarkupContainer("ie6FixIframe").setVisible(show));
        
        container.add(titleLabel);
        
        final Link closeLink = new AjaxFallbackLink("closeLink") {
            public void onClick(final AjaxRequestTarget target) {
                close(target);
            }
        };
        closeLink.setOutputMarkupId(true);
        container.add(closeLink);
        
        add(new WebMarkupContainer("scripts") {
            protected void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
                replaceComponentTagBody(markupStream, openTag, getDraggableJavascript());
            }
        });
        
        add(JavascriptPackageResource.getHeaderContribution(CommonResources.JS_JQUERY),
            JavascriptPackageResource.getHeaderContribution(CommonResources.JS_JQUERY_UI_CORE),
            JavascriptPackageResource.getHeaderContribution(CommonResources.JS_JQUERY_UI_DRAGGABLE),
            JavascriptPackageResource.getHeaderContribution(CommonResources.JS_JQUERY_BGIFRAME));
    }
    
	public String getContentId() {
		return CONTENT_ID;
	}
	
	/**
	 * Setzt den Content f�r den modalen Dialog.
	 * @param content Die Content Komponente.
	 */
	public void setContent(final Component content){
		if (CONTENT_ID.equals(content.getId())){
			container.addOrReplace(content);
		} else {
			throw new IllegalArgumentException("ID of content must be 'content' but was " + content.getId());
		}
	}
	
	 /**
     * Setzt den Titel des Panels.
     * @param title Titel des Panels
     * @return This
     */
    public ModalDialog setTitle(final IModel<String> title) {
        this.titleLabel.setDefaultModel(title);
        return this;
    }
	
    /**
     * Zeigt den Dialog an. Im Non-Ajax-Mode kann das {@link AjaxRequestTarget} <code>null</code> sein!
     * @param target Das target f�r die zu aktualisierenden Komponenten.
     */
    public void show(final AjaxRequestTarget target) {
        setVisible(true);
        if (target != null) {
            target.addComponent(this);
        } else {
            // Non AJAX-Request --> JavaScript ausgeschaltet
            get("container:ie6FixIframe").setVisible(true);
        }
    }

    /**
     * Schlie�t den Dialog. Im Non-Ajax-Mode kann das {@link AjaxRequestTarget} <code>null</code> sein!
     * @param target Das target f�r die zu aktualisierenden Komponenten.
     */
    public void close(final AjaxRequestTarget target) {
        setVisible(false);
        if (target != null) {
            target.addComponent(this);
        }
    }
	
    /**
     * Liefert das JavaScript, um das Modale Fenster mithilfe von JQuery 'draggable' zu machen.
     * @return Den JavaScript-Schnipsel.
     */
    private String getDraggableJavascript() {
        final String containerID = get("container").getMarkupId();
        final StringBuffer sb = new StringBuffer();
        sb.append("$('#" + containerID + "').draggable({handle: '#" + titleLabel.getMarkupId() + "'});");
        sb.append("$('#" + containerID + "').bgiframe();");
        sb.append("$('#" + containerID + "').css('top',  (($(window).height())/2)-(($('#" + containerID + "').height())/2));");
        sb.append("$('#" + containerID + "').css('left',  (($(window).width())/2)-(($('#" + containerID + "').width())/2));");

        return sb.toString();
    }
    
    @SuppressWarnings("rawtypes")
	public void displayCloseLink(boolean visibility) {
    	Link closeLink = (Link) get("container" + Component.PATH_SEPARATOR + "closeLink");
    	modelChanging();
    	closeLink.setVisible(visibility);
    	modelChanged();
    	
    }

	public boolean isCloseLinkVisible() {
		return get("container" + Component.PATH_SEPARATOR + "closeLink").isVisible();
	}

}