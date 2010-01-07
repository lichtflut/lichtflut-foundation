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

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base implementation of a {@link Flow}.
 * 
 * Created: 20.06.2008
 *
 * @author Oliver Tigges
 */
public abstract class BaseFlow implements Flow {
	
	private int currentStepIndex = -1;
	private final List<Step> steps = new ArrayList<Step>();
	private final FlowDataTransfer transfer = new FlowDataTransfer();
	private final List<FlowEventListener> listeners = new ArrayList<FlowEventListener>();
	
	//-----------------------------------------------------

	/* (non-Javadoc)
	 * @see de.lichtflut.glasnost.app.control.flow.base.Flow#start()
	 */
	public void start() {
	}
	
	public boolean hasNext() {
		return currentStepIndex < steps.size();
	}
	
	public final Step nextStep() {
		currentStepIndex++;
		if (currentStepIndex < steps.size()){
			Step step = steps.get(currentStepIndex);
			if (step.isDispensable()){
				return nextStep();
			} else {
				return step;
			}
		}
		return null;
	}
	
	public void reactivate(FlowDataTransfer subFlowResult) {
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.glasnost.app.control.flow.base.Flow#terminate()
	 */
	public final FlowDataTransfer terminate() {
		fireTerminationEvent();
		return transfer;
	}
	
	public final void addStep(Step step){
		steps.add(step);
		step.setTransfer(transfer);
	}
	
	public final void addStep(Step step, int index){
		steps.add(index, step);
		step.setTransfer(transfer);
	}
	
	public void addFlowListener(FlowEventListener listener) {
		this.listeners.add(listener);
	}
	
	//-----------------------------------------------------
	
	@Override
	public String toString() {
		return getClass().getCanonicalName();
	}
	
	//-----------------------------------------------------
	
	protected Step currentStep(){
		return steps.get(currentStepIndex);
	}
	
	protected void fireTerminationEvent(){
		// iterate over index because list could be modified
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).flowTerminated();
		}
	}
	
}
