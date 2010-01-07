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
package de.lichtflut.infra.ui;

import java.awt.Image;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * cache for images.
 * 
 * 
 * @author Oliver Tigges
 * 
 * Created: 11.03.2009
 *
 * Description:
 */
public class ImageCache {
	
	public final static ImageCache IC = new ImageCache();
	
	private final Map<String, Image> imageMap = new WeakHashMap<String, Image>();
	
	//------------------------------------------------------
	
	private ImageCache() {
	}
	
	//------------------------------------------------------
	
	public boolean contains(String url){
		return imageMap.containsKey(url);
	}
	
	public Image get(String url){
		return imageMap.get(url);
	}
	
	public void put(String url, Image image){
		imageMap.put(url, image);
	}

}
