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
 * Flow that consists of a single step.
 * 
 * Created: 18.07.2008
 * 
 * @author Oliver Tigges
 */
public abstract class SingleStepFlow extends BaseFlow implements Step {

	public SingleStepFlow() {
		addStep(this);
	}
	
	//-----------------------------------------------------
	
	public InteractiveStepView createFlowView(FlowExecutor flowExecutor) {
		throw new UnsupportedOperationException();
	}

	public boolean isDispensable() {
		return false;
	}

	public boolean isInteractive() {
		return false;
	}

	public void setTransfer(FlowDataTransfer transfer) {
		// transfer not needed for single step flows
	}

}
