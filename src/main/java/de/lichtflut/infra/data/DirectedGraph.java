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
package de.lichtflut.infra.data;

import java.util.Collection;
import java.util.HashSet;


/**
 * Copyright 2006 by Oliver Tigges
 *
 * @author Oliver Tigges
 *
 * Created: 12.03.2006
 * 
 * Description: The DirectedGraph with the possible routes through a Stream
 */
public class DirectedGraph implements Cloneable{

	/**
	 * DefaultConstructor
	 */
	public DirectedGraph(){ 
		START = new DirectedNode() {

			public boolean isOptional() {
				return false;
			}
			
			public String toString() {
				return getName();
			}
			
			@Override
			public String getName() {
				return "START";
			}

		};
	}
	
	//-----------------------------------------------------
	
	/**
	 * Adds a new Node at the Beginning of this DirectedGraph 
	 */
	public void addInitialNode(DirectedNode node){
		START.append(node);		
	}
	
	/**
	 * @return Collection of initial WebNodes
	 */
	public Collection<DirectedNode> getInitialNodes(){
		return START.getSuccessors();
	}

	/**
	 * Returns String representation
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (DirectedNode node : START.getSuccessors()) {
			sb.append(node.toString("", new HashSet<DirectedNode>()));	
		}
		return sb.toString();
	}
	
	// ------------------------------------------------------
	
	// Pseudo Node for Beginning 
	protected final DirectedNode START;
	
}
