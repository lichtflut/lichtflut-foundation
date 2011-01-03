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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Node of a {@link DirectedGraph}.
 *
 * Created: 12.03.2006
 * 
 * @author Oliver Tigges
 */
public abstract class DirectedNode {
	
	private List<DirectedNode> predecessors = new ArrayList<DirectedNode>();
	private Set<DirectedNode> successors = new HashSet<DirectedNode>();
	
	private boolean endNode = false;
	
	// -----------------------------------------------------
	
	/**
	 * Append a new DirectedNode.
	 * Successors are sorted, first mandatory, than optional
	 * @param successor
	 */
	public void append(DirectedNode successor){
		if (successors.contains(successor)){
			return;
		}
		addSuccessor(successor);	
		successor.addPredecessor(this);
		
		//delegate to predecessors
		if (isOptional() && successor != this){
			for (DirectedNode predecessor : predecessors) {
				if (predecessor != this && predecessor != successor){
					predecessor.append(successor);
				}
			}
		}
	}
	
	/**
	 * @return a Collection of WebNodes
	 */
	public Collection<DirectedNode> getSuccessors() {
		return successors;
	}
	
	public void addPredecessor(DirectedNode predecessor) {
		predecessors.add(predecessor);
	}
	
	public void makeEndNode() {
		if (isEndNode()){
			return;
		}
		endNode = true;
		if (isOptional()){
			for (DirectedNode predecessor : predecessors) {
				predecessor.makeEndNode();
			}
		}
	}
	
	public boolean isEndNode() {
		return endNode;
	}
	
	// -----------------------------------------------------
	
	public abstract String getName(); 
	
	public abstract boolean isOptional();
		
	// ------------------------------------------------------
	
	
	protected String toString(String indent, Set<DirectedNode> alreadyDumped) {
		alreadyDumped.add(this);
		StringBuffer sb = new StringBuffer(indent + " + NODE ");
		if (isOptional()){
			sb.append("[" + toString() + "]\n");
		} else {
			sb.append(toString() + "\n");
		}
		for (DirectedNode node : successors) {
			if (!alreadyDumped.contains(node)){
				sb.append(indent + node.toString(indent + " |", alreadyDumped));
			}
		}
		return sb.toString();
	}
	
	protected void addSuccessor(DirectedNode node){
		successors.add(node);
	}
	
}
