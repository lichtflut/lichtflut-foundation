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
package de.lichtflut.infra.http;

import java.util.Collections;

import junit.framework.TestCase;
import de.lichtflut.infra.http.HttpClient;
import de.lichtflut.infra.http.HttpRequestException;

/**
 * 
 * Copyright 2008 by Oliver Tigges
 * 
 * @author Oliver Tigges
 * 
 * Created: 02.12.2008
 *
 * Description:
 */
public class HttpClientTest extends TestCase {

	public void testGet(){
		HttpClient client = new HttpClient();
		try {
			client.send("http://www.heise.de", "GET", Collections.<String, Object>emptyMap());
		} catch (HttpRequestException e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
