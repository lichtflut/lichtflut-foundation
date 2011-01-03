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
package de.lichtflut.infra.persistence;


/**
 * 
 * Copyright 2005 by Oliver Tigges
 *
 * @author Oliver Tigges
 *
 * Created: 15.05.2005
 * 
 * Description: Database based Provider for Unique IDs
 */
public class UniqueIdProvider {
	
	public UniqueIdProvider(UniqueIdReservator reservator){
		this.reservator = reservator;
		reserveMoreIds();
	}
	
	public Long nextId(){
		if (nextId > maxReservedId){
			reserveMoreIds();
		}
		return nextId++;
	}
	
	//------------------------------------------------------ 
	
	private void reserveMoreIds(){
		maxReservedId = this.reservator.reserve();
		if (maxReservedId < nextId){
			throw new IllegalStateException();
		}
	}
	
	//------------------------------------------------------ 
	
	private final UniqueIdReservator reservator;
	
	private long nextId;
	private long maxReservedId;

}
