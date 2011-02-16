package de.lichtflut;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import de.lichtflut.stuff.wicket.modaldialog.ModalDialogDemoApplication;
import de.lichtflut.stuff.wicket.modaldialog.ModalDialogDemoPage;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage
{
	private WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new ModalDialogDemoApplication());
	}

	@Test
	public void homepageRendersSuccessfully()
	{
		//start and render the test page
		tester.startPage(ModalDialogDemoPage.class);

		//assert rendered page class
		tester.assertRenderedPage(ModalDialogDemoPage.class);
	}
}
