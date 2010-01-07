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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * Test class for SqlExecuter
 * 
 * Created: 21.08.2009
 *
 * @author R. Knox
 */
public class SqlExecuterTest extends TestCase{
	
	private SqlExecutor exec = null;
	
	protected void setUp(){
		exec = new SqlExecutor("jdbc:mysql://localhost/", "root", "");
	}
	
	public void testCreateSqlStmts() throws SQLException, IOException{
		final StringBuffer sb = new StringBuffer();
		sb.append("DROP DATABASE IF EXISTS glasnost_local;\n");
		sb.append("/*CREATE DATABASE glasnost_local CHARACTER SET utf8 COLLATE utf8_bin;\n");
		sb.append("CREATE DATABASE glasnost_local CHARACTER SET utf8 COLLATE utf8_bin;*/\n");
		sb.append("--USE glasnost_local;\n");
		sb.append("GRANT ALL ON *.* TO 'root'@'localhost' identified by '';\n");
		
		final List<String> stmts = 	exec.createSqlStmts(new StringReader(sb.toString()));
		assertEquals(2, stmts.size());
		assertEquals("DROP DATABASE IF EXISTS glasnost_local", stmts.get(0));
		assertEquals("GRANT ALL ON *.* TO 'root'@'localhost' identified by ''", stmts.get(1));
	}
	
	public void testExecuteScript() throws ClassNotFoundException, SQLException, IOException{
		exec.createSqlStmts(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("testCreateDatabase.sql")));
		exec.initMysql();
		//exec.executeStmts(list);
		}
	
	public void testSqlExecuter() throws IOException, SQLException{
		exec.createSqlStmts(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("testCreateDatabase.sql")));
		//exec.executeStmts(sqlStmtList);
	}

}
