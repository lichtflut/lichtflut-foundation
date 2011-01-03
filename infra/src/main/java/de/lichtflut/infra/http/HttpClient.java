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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import de.lichtflut.infra.io.CompleteReader;
import de.lichtflut.infra.io.Streamer;
import de.lichtflut.infra.logging.Log;

/**
 * 
 * Copyright 2009 by Oliver Tigges
 * 
 * @author Oliver Tigges
 * 
 * Created: 02.12.2008
 *
 * Description: simple http client
 */
public class HttpClient {
	
	private final CompleteReader cr = new CompleteReader();
	private final Streamer streamer = new Streamer();
	
	private final String useragent;
	
	//-----------------------------------------------------
	
	public HttpClient() {
		this("http-client");
	}
	
	public HttpClient(String useragent){
		this.useragent = useragent;
	}
	
	//-----------------------------------------------------

	public void send(String baseUrl, String method, Map<String, Object> params) throws HttpRequestException{
		String fullUrl = baseUrl + toRequestParamaters(params);
		
		try {
			URL url = new URL(fullUrl);
		
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			for(String key: params.keySet()){
				connection.setRequestProperty(key, params.get(key).toString());	
			}
			int rc = connection.getResponseCode();
			Log.info(this, method + " " + fullUrl + " --> " + rc);
			
		} catch (MalformedURLException e) {
			throw new HttpRequestException(e);
		} catch (IOException e) {
			throw new HttpRequestException(e);
		}
	}
	
	public InputStream sendStream(String baseUrl, String method, Map<String, Object> params, InputStream in) throws HttpRequestException{
		String fullUrl = baseUrl + toRequestParamaters(params);
		
		try {
			URL url = new URL(fullUrl);
		
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			for(String key: params.keySet()){
				connection.setRequestProperty(key, params.get(key).toString());	
			}
			OutputStream outputStream = connection.getOutputStream();
			
			streamer.stream(in, outputStream);
			
			int rc = connection.getResponseCode();
			Log.info(this, method + " " + fullUrl + " --> " + rc);
			
			if (HttpURLConnection.HTTP_OK != rc){
				String error = cr.toString(connection.getErrorStream());
				throw new HttpRequestException(rc, error);
			}
			
			return connection.getInputStream();
			
		} catch (MalformedURLException e) {
			throw new HttpRequestException(e);
		} catch (IOException e) {
			throw new HttpRequestException(e);
		}
	}
		
	
	public InputStream read(String baseUrl, String method, Map<String, Object> params) throws HttpRequestException{
		String fullUrl = baseUrl + toRequestParamaters(params);
		try {
			URL url = new URL(fullUrl);
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setRequestProperty("User-Agent", useragent);

			for(String key: params.keySet()){
				connection.setRequestProperty(key, params.get(key).toString());	
			}
			
			int rc = connection.getResponseCode();
			Log.info(this, method + " " + fullUrl + " --> " + rc);
			
			if (HttpURLConnection.HTTP_OK != rc){
				String error = cr.toString(connection.getErrorStream());
				throw new HttpRequestException(rc, error);
			}
			
			return connection.getInputStream();
			
		} catch (MalformedURLException e) {
			throw new HttpRequestException(e);
		} catch (IOException e) {
			throw new HttpRequestException(e);
		}
	}
	
	public InputStream read(String baseUrl, String method) throws HttpRequestException{
		return read(baseUrl, method, Collections.<String, Object>emptyMap());
	}
	
	//------------------------------------------------------
	
	public HttpURLConnection connect(String baseUrl, String method, Map<String, Object> params) throws HttpRequestException{
		String fullUrl = baseUrl + toRequestParamaters(params);
		try {
			URL url = new URL(fullUrl);
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			for(String key: params.keySet()){
				connection.setRequestProperty(key, params.get(key).toString());	
			}
			Log.info(this, method + " " + fullUrl + " --> opened for streaming");
			return connection;
			
		} catch (MalformedURLException e) {
			throw new HttpRequestException(e);
		} catch (IOException e) {
			throw new HttpRequestException(e);
		}
	}
	
	//-----------------------------------------------------
	
	private String toRequestParamaters(Map<String, Object> params){
		StringBuffer sb = new StringBuffer("?");
		for(Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();){
			String key = iterator.next();
			sb.append(key + "=" + params.get(key).toString());
			if (iterator.hasNext()){
				sb.append("&");
			}
		}
		return sb.toString();
	}
	
}
