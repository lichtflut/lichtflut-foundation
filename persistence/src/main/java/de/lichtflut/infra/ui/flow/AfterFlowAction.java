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
