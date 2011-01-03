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
package de.lichtflut.infra.jdbc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import de.lichtflut.infra.logging.Log;

/**
 * Simple executor for SQL.
 * 
 * Created: 20.08.2009
 * 
 * @author Ravi Knox
 */
public class SqlExecutor {

	private final String url;
	private final String user;
	private final String password;
	
	// ------------------------------------------------------

	/**
	 * Constructor.
	 */
	public SqlExecutor(final String url, final String user, final String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}

	// -------------------------------------------------------

	public void initMysql() throws ClassNotFoundException {
		initDriver(JdbcDrivers.MYSQL);
	}

	public void initDerby() throws ClassNotFoundException {
		initDriver(JdbcDrivers.DERBY_EMBEDDED);
	}

	// -------------------------------------------------------

	/**
	 * Processes given SQL file.
	 * @param file The file to be processed.
	 * @param continueOnError Flag indicating if processing shall be continued on errors.
	 */
	public void process(final File file, final boolean continueOnError) throws IOException, SQLException{
		process(new FileReader(file), continueOnError);
	}
	
	/**
	 * Processes given reader providing SQL statements.
	 * @param reader The reader to be processed.
	 * @param continueOnError Flag indicating if processing shall be continued on errors.
	 */
	public void process(final Reader reader, final boolean continueOnError) throws IOException, SQLException {
		final List<String> stmts = createSqlStmts(reader);
		executeStmts(stmts, continueOnError);
	}

	/**
	 * Parses the given file and creates a list of the contained SQL statements.
	 */
	public List<String> createSqlStmts(final File file) throws IOException {
		return createSqlStmts(new FileReader(file));
	}

	/**
	 * Parses the given reader and creates a list of the contained SQL statements.
	 */
	public List<String> createSqlStmts(final Reader reader) throws IOException {
		final List<String> sqlStmts = new ArrayList<String>();
		final BufferedReader br = new BufferedReader(reader);
		
		String line = br.readLine();
		boolean inComment = false;
		while (line != null) {
			line = line.trim();
			if (line.startsWith("/*")) {
				inComment = true;
			} else if ((!line.startsWith("--")) && (!inComment && line.length() > 1)) {
				if (line.endsWith(";")){
					line = line.substring(0, line.lastIndexOf(";"));
				}
				sqlStmts.add(line);
			}
			
			if (line.endsWith("*/")) {
				inComment = false;
			}
			line = br.readLine();
		}
		return sqlStmts;
	}

	/**
	 * Executes each SQL statement in list.
	 * @param sqlStmts List of SQL statements.
	 * @throws SQLException
	 * @throws IOException
	 */
	public void executeStmts(final List<String> sqlStmts) throws SQLException, IOException {
		executeStmts(sqlStmts, false);
	}
	
	/**
	 * Executes each SQL statement in list.
	 * @param sqlStmts List of SQL statements.
	 * @param continueOnError Flag indicating if the processing shall be continued on error.
	 * @throws SQLException
	 * @throws IOException
	 */
	public void executeStmts(final List<String> sqlStmts, final boolean continueOnError) throws SQLException, IOException {
		final Connection con = DriverManager.getConnection(url, user, password);
		Log.debug(this, "Connection opened.");
		ListIterator<String> li = sqlStmts.listIterator();
		while (li.hasNext()) {
			String line = li.next();
			Log.debug(this, line);
			try {
				executeStmt(con, line);
			} catch (SQLException sqle){
				if (continueOnError){
					Log.error(this, sqle.getMessage());
				} else {
					con.close();
					throw sqle;
				}
			}
		}
		con.close();
		Log.debug(this, "Connection closed.");
	}

	// ----------------------------------------------------------
	
	protected void executeStmt(final Connection con, final String stmt) throws SQLException {
		final PreparedStatement ps = con.prepareStatement(stmt);
		try {
			ps.execute();
		} finally {
			ps.close();
		}
	}
	
	/**
	 * Initializes the given JDBC driver.
	 * @param driver The full qualified class name of the driver.
	 * @throws ClassNotFoundException
	 */
	protected void initDriver(final String driver) throws ClassNotFoundException {
		try {
			Class.forName(driver).newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException("Couldn't initialize JDBC driver '" + driver + "'", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Couldn't initialize JDBC driver '" + driver + "'", e);
		}
	}

}
