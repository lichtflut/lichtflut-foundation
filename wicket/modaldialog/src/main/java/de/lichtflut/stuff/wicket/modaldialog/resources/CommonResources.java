package de.lichtflut.stuff.wicket.modaldialog.resources;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;

/**
 * Diese Klasse stellt Konstanten f��r Resourcen bereit, die von mehreren Komponenten ben��tigt werden.
 * Sofern eine {@link ResourceReference} von mehreren Komponenten ��ber eine HeaderContribution eingebunden
 * wird, stellt die Verwendung der selben ResourceReference sicher, dass sie trotzdem nur einmal 
 * im HEAD-Bereich der Seite referenziert wird.
 * 
 * @author Oliver Tigges
 */
public final class CommonResources {

	/**
	 * ResourceReference f�r die Standard JQuery JS-Lib.
	 */
    public static final ResourceReference JS_JQUERY = new JavascriptResourceReference(
            CommonResources.class, "js/jquery-1.4.2.min.js");
    
    /**
	 * ResourceReference f�r den Core-Part der JQuery UI JS-Lib im min-Format.
	 */
    public static final ResourceReference JS_JQUERY_UI_CORE = new JavascriptResourceReference(
            CommonResources.class, "js/jquery-ui-1.8.4.core.min.js");
    
    /**
	 * ResourceReference f�r die komplette JQuery UI JS-Lib im min-Format.
	 */
    public static final ResourceReference JS_JQUERY_UI_COMPLETE = new JavascriptResourceReference(
            CommonResources.class, "js/jquery-ui-1.8.4.complete.min.js");
    
    /**
	 * ResourceReference f�r JQuery UI Draggable.
	 */
    public static final ResourceReference JS_JQUERY_UI_DRAGGABLE = new JavascriptResourceReference(
            CommonResources.class, "js/jquery-ui-1.8.4.draggable.min.js");
    
    /**
	 * ResourceReference f�r JQuery UI Resizable.
	 */
    public static final ResourceReference JS_JQUERY_UI_RESIZEABLE = new JavascriptResourceReference(
            CommonResources.class, "js/jquery-ui-1.8.4.resizeable.min.js");
    
    /**
     * BG IFrame f��gt einem Layer (z-index > 0) einen IFrame hinzu, der unsichtbar
     * im Hintergrund verhindert, dass Select-Boxen durchschimmern (IE6-Bug).
     */
    public static final ResourceReference JS_JQUERY_BGIFRAME = new JavascriptResourceReference(
            CommonResources.class, "js/jquery.bgiframe.min.js");
    
    
    private CommonResources() {
    }
}