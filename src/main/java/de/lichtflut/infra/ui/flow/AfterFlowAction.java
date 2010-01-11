/*
 * Copyright (C) 2009 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.infra.ui.flow;

/**
 * <p>
 *  Action to be executed after {@link Flow} terminated.
 * </p>
 * 
 * <p>
 * 	Created 11.01.2010
 * </p>
 *
 * @author Oliver Tigges
 */
public class AfterFlowAction implements FlowEventListener {
	
	private final Runnable action;
	
	// -----------------------------------------------------
	
	/**
	 * Creates a new action instance.
	 * @param action The action to be startet after flow termination.
	 */
	public AfterFlowAction(final Runnable action) {
		this.action = action;
	}

	// -----------------------------------------------------

	/* (non-Javadoc)
	 * @see de.lichtflut.infra.ui.flow.FlowEventListener#flowTerminated()
	 */
	public void flowTerminated() {
		new Thread(action).start();
	}

}
