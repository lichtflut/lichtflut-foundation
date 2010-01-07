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
package de.lichtflut.infra.html.provider;

import de.lichtflut.infra.html.MTIAccessor;

/**
 * This Interface is designed
 * to provide dynamic and specific search-directives for the given context 
 * 
 * Created: 05.06.2009
 * 
 * @author Nils Bleisch
 */
public interface MTICrawlerExtractionSpec {
	
	/**
	 * Created: 05.06.2009
	 * @param MTIAccessor 
	 * @author Nils Bleisch
	 */
	public void extractSpecifiedInformation(MTIAccessor accessor);
	
	
}//end of interface CrawlerExtractionSpec