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

import java.util.Properties;

import de.lichtflut.infra.jdbc.JdbcDrivers;

/**
 * profile for eclipse link JPA properties.
 * 
 * Created: 24.11.2008
 * 
 * @author Oliver Tigges
 *
 */
public class EclipseLinkProfile extends Properties {
	
	public static final String ECLIPSELINK_LOGGING_LEVEL = "eclipselink.logging.level";
	public static final String ECLIPSELINK_APPLICATION_LOCATION = "eclipselink.application-location";
	
	public static final String ECLIPSELINK_JDBC_DRIVER = "eclipselink.jdbc.driver";
	public static final String ECLIPSELINK_JDBC_URL = "eclipselink.jdbc.url";
	public static final String ECLIPSELINK_JDBC_USER = "eclipselink.jdbc.user";
	public static final String ECLIPSELINK_JDBC_PASSWORD = "eclipselink.jdbc.password";
	
	public static final String ECLIPSELINK_DDL_GENERATION = "eclipselink.ddl-generation";
	public static final String ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE = "eclipselink.ddl-generation.output-mode";
	public static final String ECLIPSELINK_CREATE_DDL_JDBC_FILE_NAME = "eclipselink.create-ddl-jdbc-file-name";
	
	public static final String ECLIPSELINK_TARGET_DATABASE = "eclipselink.target-database";
	
	//-----------------------------------------------------
	
	/**
	 * Default constructor for empty profile.
	 */
	public EclipseLinkProfile() {
		put(ECLIPSELINK_LOGGING_LEVEL, "WARNING");
	}
	
	//-----------------------------------------------------
	
	public void configureMySQL(String url, String username, String password){
		put(ECLIPSELINK_TARGET_DATABASE, "MySQL4");
		put(ECLIPSELINK_JDBC_DRIVER, JdbcDrivers.MYSQL);
		put(ECLIPSELINK_JDBC_URL, url);
		put(ECLIPSELINK_JDBC_USER, username);
		put(ECLIPSELINK_JDBC_PASSWORD, password);
	}
	
	public void configureDerbyEmbedded(String url, String username, String password){
		put(ECLIPSELINK_TARGET_DATABASE, "Derby");
		put(ECLIPSELINK_JDBC_DRIVER, JdbcDrivers.DERBY_EMBEDDED);
		put(ECLIPSELINK_JDBC_URL, url);
		put(ECLIPSELINK_JDBC_USER, username);
		put(ECLIPSELINK_JDBC_PASSWORD, password);
	}
	
	public void setLoggingLevel(String level){
		put(ECLIPSELINK_LOGGING_LEVEL, level);
	}
	
	public void enableCreateTables(){
		put(ECLIPSELINK_DDL_GENERATION, "create-tables");
		put(ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE, "database");
	}
	
	public void enableCreateDdlScripts(){
		put(ECLIPSELINK_DDL_GENERATION, "create-tables");
		put(ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE, "sql-script");
	}
	
	public void enableCreateDdlScripts(String appLocation, String fileName){
		enableCreateDdlScripts();
		put(ECLIPSELINK_APPLICATION_LOCATION, appLocation);
		put(ECLIPSELINK_CREATE_DDL_JDBC_FILE_NAME, fileName);
	}
	
}
