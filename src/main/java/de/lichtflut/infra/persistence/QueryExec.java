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

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 * Wrapper/Helper for JPA {@link Query}.
 * 
 * Created: 26.01.2009
 * 
 *  @author Oliver Tigges
 */
public class QueryExec {
	
	@SuppressWarnings("unchecked")
	public static Object firstResult(Query query){
		List resultList = query.getResultList();
		if (resultList.isEmpty()){
			return null;
		} else {
			return resultList.get(0);
		}
	}
	
	
	/**
	 * Executes the query and expects maximum one result.
	 * @param query The JPA query
	 * @return The single result or null.
	 * @throws IllegalStateException Thrown if there is more than one.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T maxOne(final Query query) throws IllegalStateException {
		List<T> resultList = query.getResultList();
		if (resultList.size() > 1){
			throw new IllegalStateException("expected maximum one result but found " 
					+ resultList.size() + ": " + resultList);
		} else if (resultList.isEmpty()){
			return null;
		} else {
			return resultList.get(0);
		}
	}
	
	/**
	 * Executes the query and expects exactly one result.
	 * @param query The JPA query
	 * @return The single result.
	 * @throws IllegalStateException Thrown if there is no result or more than one.
	 */
	public static Object exactlyOne(final Query query) throws IllegalStateException {
		try {
			return query.getSingleResult();
		} catch (NoResultException e){
			throw new IllegalStateException(e);
		} catch (NonUniqueResultException e){
			throw new IllegalStateException(e);
		}
	}

}
