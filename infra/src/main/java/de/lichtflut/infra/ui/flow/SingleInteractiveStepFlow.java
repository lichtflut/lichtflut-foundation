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
 * Flow that consists of a single interactive step.
 * 
 * Created: 18.07.2008 
 *
 * @author Oliver Tigges
 */
public abstract class SingleInteractiveStepFlow extends BaseFlow implements Step {

	private FlowDataTransfer transfer;
	
	// -----------------------------------------------------
	
	/**
	 * Creates a new {@link SingleInteractiveStepFlow}. 
	 * Sub classes just need to call this constructor to assure
	 * that this step is added to this flow.
	 */
	public SingleInteractiveStepFlow() {
		addStep(this);
	}
	
	//-----------------------------------------------------
	
	public void execute() {
		// do nothing
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	public boolean isDispensable() {
		return false;
	}

	public boolean isInteractive() {
		return true;
	}

	public void setTransfer(FlowDataTransfer transfer) {
		this.transfer = transfer;
	}
	
	public void addTransfer(String key, Object value){
		transfer.putData(key, value);
	}
	
	public Object getTransfer(String key){
		return transfer.getData(key);
	}
	
	
}
