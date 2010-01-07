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

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;


/**
 * Numeric representation of a probability. Internally the probability is expressed
 * by a floating point value between 0.0 and 1.0 where 0.0 is least probability and
 * 1.0 is maximum probability.
 * 
 * A {@link Probability} is immutable.
 * 
 * Created: 16.10.2007
 *
 * @author Oliver Tigges
 * 
 */
public class Probability implements Comparable<Probability>{
	
	protected static final double BORDER_MIN = 0.0d;

	protected static final double BORDER_MAX = 1.0d;

	protected static final double AVERAGE = 0.5d;
	
	public static final Probability MAX = new Probability(BORDER_MAX);
	
	public static final Probability MIN = new Probability(BORDER_MIN);
	
	public static final Probability AVG = new Probability(AVERAGE);
	
	public static final DecimalFormat format = new DecimalFormat("#.###");
	
	//-----------------------------------------------------
	
	private final double p;
	
	//-- STATIC METHODS -----------------------------------
	
	public static Probability valueOf(String value) throws ParseException {
		return new Probability(DecimalFormat.getInstance(Locale.ENGLISH).parse(value));
	}
	
	//-----------------------------------------------------
	
	/**
	 * Creates a new {@link Probability} based on a double value. The value has to be between
	 * 0.0 and 1.0.
	 */
	public Probability(final double probability) {
		if (probability < BORDER_MIN || probability > BORDER_MAX){
			throw new IllegalArgumentException("probabilty as double must be between 0.0 and 1.0");
		}
		this.p = probability;
	}
	
	/**
	 * Creates a new {@link Probability} based on a number. The value has to be between
	 * 0.0 and 1.0.
	 */
	public Probability(final Number number) {
		this(number.doubleValue());
	}
	
	/**
	 * Creates a new {@link Probability} based on another {@link Probability}. 
	 */
	public Probability(final Probability other) {
		this.p = other.p;
	}
	
	//-----------------------------------------------------
	
	public double getDouble(){
		return p;
	}
	
	public Probability add(Probability prob) {
		return new Probability(Math.min(BORDER_MAX, p + prob.p));
	}
	
	public Probability add(double d) {
		d = Math.max(BORDER_MIN, d);
		return new Probability(Math.min(BORDER_MAX, p + d));
	}
	
	public Probability weight(Probability prob) {
		return new Probability(Math.min(BORDER_MAX, p * prob.p));
	}
	
	public Probability weight(double d) {
		d = Math.max(BORDER_MIN, d);
		return new Probability(Math.min(BORDER_MAX, p * d));
	}
	
	public boolean greaterEquals(Probability comp) {
		return p >= comp.p;
	}
	
	public boolean greater(Probability comp) {
		return p > comp.p;
	}
	
	public boolean lessThan(Probability comp) {
		return p < comp.p;
	}
	
	//----------------------------------------------------- 
	
	@Override
	public String toString() {
		return format.format(p);
	}
	
	public int compareTo(Probability other) {
		return (int) ((other.p - p) * 1000000);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Probability){
			Probability other = (Probability) obj;
			return p == other.p;
		} 
		return false;
	}

}
