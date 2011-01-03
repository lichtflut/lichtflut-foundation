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
 * A flow encapsulates a sequence of automatic or interactive steps.
 * 
 * Created: 02.03.2008 
 *
 * @author Oliver Tigges
 */
public interface Flow {
	
	/**
	 * starts the flow.
	 */
	void start();

	Step nextStep();
	
	boolean hasNext();
	
	/**
	 * Called when the flow is reactivated after a sub flow has finished
	 * The given {@link FlowDataTransfer} may contain data the sub flow produced. This data
	 * should be processed inside the reactivate method becaus it will be disposed afterwards.
	 * @param subFlowResult
	 */
	void reactivate(FlowDataTransfer subFlowResult);
	
	FlowDataTransfer terminate();
	
	void addFlowListener(FlowEventListener listener);

}