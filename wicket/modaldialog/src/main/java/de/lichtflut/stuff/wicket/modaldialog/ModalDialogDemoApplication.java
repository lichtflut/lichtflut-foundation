package de.lichtflut.stuff.wicket.modaldialog;

import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see de.lichtflut.Start#main(String[])
 */
public class ModalDialogDemoApplication extends WebApplication
{    	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<ModalDialogDemoPage> getHomePage()
	{
		return ModalDialogDemoPage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

		// add your configuration here
	}
}
