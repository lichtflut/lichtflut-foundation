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
/**
 * 
 */
package de.lichtflut.infra.persistence;

/**
 * Copyright 2008 by Oliver Tigges
 * 
 * @author Oliver Tigges
 * 
 * Created: 26.03.2008
 *
 * Description: 
 */
public class SimpleIdReservator implements UniqueIdReservator {

	public SimpleIdReservator() {
		this.currentMax = 0;
		this.blocksize = 10000;
	}
	
	public SimpleIdReservator(long offset, int blocksize){
		this.currentMax = offset;
		this.blocksize = blocksize;
	}
	
	/* (non-Javadoc)
	 * @see org.arastreju.persistence.UniqueIdReservator#reserve()
	 */
	public Long reserve() {
		currentMax = currentMax + blocksize;
		return currentMax;
	}
	
	//-----------------------------------------------------
	
	private long currentMax;
	private final int blocksize;

}
