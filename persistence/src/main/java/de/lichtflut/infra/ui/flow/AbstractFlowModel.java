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

import de.lichtflut.infra.Infra;
import de.lichtflut.infra.ui.model.ModelChangeEvent;
import de.lichtflut.infra.ui.model.ModelChangeType;
import de.lichtflut.infra.ui.model.ModelObserver;

/**
 * Abstract base for models backing flow views.
 * 
 * Created: 19.05.2009
 *
 * @author Oliver Tigges 
 */
public abstract class AbstractFlowModel {
	
	public static final String ACTION_SOURCE_ANY = "action.source.any";
	
	private final Map<ModelObserver<?>, String> listeners = new HashMap<ModelObserver<?>, String>();
	
	// ------------------------------------------------------
	
	/**
	 * Add a listener notified about changes.
	 * @param actionId The action Id
	 */
	public void addChangeListener(final ModelObserver<?> listener){
		this.listeners.put(listener, ACTION_SOURCE_ANY);
	}
	
	/**
	 * Add a listener notified about changes.
	 * @param actionId The action Id
	 */
	public void addChangeListener(final ModelObserver<?> listener, final String actionId){
		this.listeners.put(listener, actionId);
	}
	
	// ------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	protected void fireModelChange(){
		fireModelChange(new ModelChangeEvent(ACTION_SOURCE_ANY));
	}
	
	@SuppressWarnings("unchecked")
	protected void fireModelChange(final String id){
		fireModelChange(new ModelChangeEvent(id));
	}
	
	protected void fireModelChange(final String id, final Object affectedObject, final ModelChangeType type){
		fireModelChange(new ModelChangeEvent<Object>(id, affectedObject, type));
	}
	
	@SuppressWarnings("unchecked")
	protected void fireModelChange(final ModelChangeEvent<?> e){
		for (ModelObserver l : listeners.keySet()) {
			final String source = listeners.get(l);
			if (ACTION_SOURCE_ANY.equals(source) || Infra.equals(e.getSource(), source)){
				l.modelChanged(e);
			}
		}
	}

}
