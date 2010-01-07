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
package de.lichtflut.infra.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * Copyright 2008 by Oliver Tigges
 * 
 * @author Oliver Tigges
 * 
 * Created: 08.12.2008
 *
 * Description:
 */
public class Crypt {
	
	private static final String MD5 = "MD5";

	public static String md5Hex(Object obj){
		StringBuffer sb = new StringBuffer();
		byte[] hash = md5(obj);
		for (byte b : hash) {
			int i = (255 + b) % 255; 
			sb.append(Integer.toHexString(i));
		}
		return sb.toString();
	}
	
	public static byte[] md5(Object obj){
		try {
			MessageDigest md5 = MessageDigest.getInstance(MD5);
			return md5.digest(obj.toString().getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	//-----------------------------------------------------
	
	public static void main(String[] args){
		String orig = "root";
		System.out.println("md5 for '" + orig + "' ==> " + md5(orig));
		System.out.println("md5Hex for '" + orig + "' ==> " + md5Hex(orig));
	}

}
