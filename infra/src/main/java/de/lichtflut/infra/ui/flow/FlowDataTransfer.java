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

import java.util.HashMap;
import java.util.Map;

/**
 * Simple data holder for values transfered between steps of a flow.
 * 
 * Created: 20.06.2008
 *
 * @author Oliver Tigges
 */
public class FlowDataTransfer {
	
	private final Map<String, Object> data = new HashMap<String, Object>();
	
	//-----------------------------------------------------

	public void putData(String key, Object value){
		data.put(key, value);
	}
	
	public Object getData(String key){
		return data.get(key);
	}
	
	// ------------------------------------------------------
	
	public void dispose(){
		data.clear();
	}
	
}
