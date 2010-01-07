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

import de.lichtflut.infra.Infra;
import de.lichtflut.infra.threading.Order;


/**
 * A handle (reference) of a {@link Flow}.
 * 
 * Created: 17.10.2008
 *
 * @author Oliver Tigges
 */
public class FlowHandle implements FlowEventListener, Order {

	private final Flow flow;
	private final FlowExecutor executor;
	
	private boolean terminated = false;
	
	//-----------------------------------------------------
	
	/**
	 * Creates a new flow handle.
	 * @param flow The flow.
	 * @param executor The flow executor.
	 */
	public FlowHandle(final Flow flow, final FlowExecutor executor) {
		this.flow = flow;
		this.executor = executor;
	}
	
	//-----------------------------------------------------
	
	/**
	 * Starts the execution of the flow in a separate thread and returns immediately.
	 */
	public void startFlow(){
		executor.getOrderExecutor().addOrder(this);
	}
	
	/**
	 * Executes the flow and returns when it is finished.
	 */
	public void execute(){
		executor.stepForward();
		while (!isTerminated()){
			Infra.wait(100);
		}
	}
	
	/**
	 * Add a new listener.
	 */
	public void addListener(FlowEventListener listener){
		flow.addFlowListener(listener);
	}
	
	//-----------------------------------------------------
	
	/* (non-Javadoc)
	 * @see de.lichtflut.glasnost.app.control.flow.base.FlowEventListener#flowTerminated()
	 */
	public void flowTerminated() {
		terminated = true;
	}
	
	public boolean isTerminated() {
		return terminated;
	}
	
}
