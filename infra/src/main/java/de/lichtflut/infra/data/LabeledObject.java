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

/**
 * An object with an attached label.
 * 
 * Created: 21.07.2008
 *
 * @author Oliver Tigges
 */
public class LabeledObject<T> {
	
	private String label;
	
	private T value;
	
	//-----------------------------------------------------
	
	/**
	 * Creates a new labeled object.
	 */
	public LabeledObject(String label, T value) {
		this.label = label;
		this.value = value;
	}

	//----------------------------------------------------- 

	public T getValue(){
		return value;
	}
	
	public String getLabel() {
		return label;
	}
	
	public boolean equalsLabel(final String other){
		return label.equals(other);
	}

	public boolean equalsValue(final T other){
		return value.equals(other);
	}
	
	//-----------------------------------------------------
	
	@Override
	public String toString() {
		return label;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LabeledObject){
			LabeledObject<T> other = (LabeledObject) obj;
			return equalsLabel(other.label) && equalsValue(other.value);
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
}
