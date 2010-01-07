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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.lichtflut.infra.logging.Log;

/**
 * Calculates distances between two Strings.
 *
 * Created: 15.05.2005
 * 
 * @author Oliver Tigges
 */
public class DistanceCalculator {

	/**
	 * Calculates the distance of the Strings a and b
	 * @param a
	 * @param b
	 * @return a value between 0.0 and 100.0
	 */
	public double distance(String a, String b) {
		List<String> commonMorphemes = commonMorphemes(a, b);
		double percentageDifferentLetters = percentageDifferentLetters(a,b, commonMorphemes);
		double percentageDisorderedLetters = percentageDisorderredLetters(a,b, commonMorphemes);
		
		return percentageDifferentLetters + percentageDisorderedLetters ;
	}

	/**
	 * Prints the distance of a and b.
	 * @param a
	 * @param b
	 */
	public void printDistance(String a, String b) {
		Log.info(this, "Distance of '" + a + "' | '" + b + "':\t"
				+ NumberFormat.getNumberInstance().format(distance(a, b)));
	}

	//------------------------------------------------------

	/**
	 * calculates the percentage of letters contained in a but not in b and vice versa
	 */
	private double percentageDifferentLetters(String a, String b, List<String> commonMorphemes){
		double differentLetters = differentLetters(a, b, commonMorphemes);
		double totalLenght = a.length() + b.length();
		return differentLetters / totalLenght * 100.0;
	}

	/**
	 * Calculates the percentage of common letters/morphemes that are on 
	 * different positions in a and b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private double percentageDisorderredLetters(String a, String b, List<String> commonMorphemes){
		double avgLength = (a.length() + b.length()) / 2.0;
				
		int disorderedLetters = Math.max(0, commonMorphemes.size() -1);
		return disorderedLetters / avgLength * 100.0;
	}
	
	/**
	 * Adds up the letters contained in a and not in b and vice versa
	 * @param a
	 * @param b
	 * @return
	 */
	private int differentLetters(String a, String b, List<String> commonMorphemes){
		for (String element : commonMorphemes) {
			a = remove(a, element);
			b = remove(b, element);
		}
		return a.length() + b.length();
	}
	
	/**
	 * Returns a List (of Strings) with common Morphemes of a and b
	 * @param a
	 * @param b
	 * @return List (of Strings) sorted by morpheme size desc
	 */
	private List<String> commonMorphemes(String a, String b){
		List<String> commonMorphemes = new ArrayList<String>();
		for (int i = a.length(); i > 0; i--) {
			Set<String> morphems = getMorphemes(a, i);
			for (Iterator<String> iter = morphems.iterator(); iter.hasNext();) {
				String element = iter.next();
				if (contains(b, element)){
					commonMorphemes.add(element);
					a = remove(a, element);
					b = remove(b, element);
				}
			}
			if (i > a.length()){
				i = a.length();
			}			
		}
		return commonMorphemes;
	}

	/**
	 * Returns a Set of all possible morphems of the term with the given size
	 */
	private Set<String> getMorphemes(String term, int morphemSize) {
		Set<String> morphems = new HashSet<String>();
		int pos = 0;
		while (term.length() > (pos + morphemSize - 1)) {
			morphems.add(term.substring(pos, pos + morphemSize));
			pos++;
		}
		return morphems;
	}
	
	/**
	 * indicates wheter the term contains the given pattern
	 * @param term
	 * @param pattern
	 * @return
	 */
	private boolean contains(String term, String pattern) {
		return term.indexOf(pattern) > -1;
	}
	
	/**
	 * Removes the first occurence of the given patterns from the term
	 * @param term
	 * @param toRemove
	 * @return the result string
	 */
	private String remove(String term, String toRemove){
		int pos = term.indexOf(toRemove);
		if (pos == -1)
			return term;
		return term.substring(0, pos) + term.substring(pos + toRemove.length());
	}

	//------------------------------------------------------

	public static void main(String[] args) {
		DistanceCalculator dc = new DistanceCalculator();
		dc.printDistance("weihnachten", "weihnachten");
		dc.printDistance("a", "a");
		dc.printDistance("bosten", "osten");
		dc.printDistance("osten", "ostern");
		dc.printDistance("weihnachten", "weihnachtlich");
		dc.printDistance("weihnachten", "weihen");
		dc.printDistance("zeigen", "gezeigt");
		dc.printDistance("weihnachten", "ostern");
		dc.printDistance("b", "a");
		dc.printDistance("weinachten", "a");
	}

}