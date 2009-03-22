/* Adolfo Estevez Jimenez: aestevezjimenez@gmail.com
 * Copyright 2007-2009
 * http://code.google.com/p/jcontenedor,
 * http://code.google.com/p/jembedded,
 * http://code.google.com/p/jservicerules
 * http://semanticj2ee.blogspot.com/ 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jsemantic.jintegration.test.database.skeletal;

import java.sql.ResultSet;

import org.jsemantic.jintegration.test.core.skeletal.AbstractIntegrationTest;
import org.jsemantic.jintegration.test.database.DatabaseIntegrationTest;
import org.jsemantic.services.dbservice.DBServer;


public abstract class AbstractDatabaseIntegrationTest extends AbstractIntegrationTest implements DatabaseIntegrationTest{ 
	
	protected void setUp() throws Exception {
		init();
	}

	protected void tearDown() throws Exception {
		release();
	}

	protected void init() throws Exception {
		super.init();
	}

	protected void release() throws Exception {
		super.release();
	}
	
	protected DBServer getDBService() {
		return ((DBServer) getService("dbService"));
	}

	public ResultSet executeQuery(String query) {
		return getDBService().executeQuery(query);
	}

	public int executeUpdate(String query) {
		return getDBService().executeUpdate(query);
	}

	public void assertResulSetNotNull(String query) {
		super.assertNotNull(executeQuery(query));
	}

	public void assertResulSetNull(String query) {
		super.assertNull(executeQuery(query));
	}

	public void assertEqualsUpdateResult(String query, int result) {
		super.assertEquals(result, executeUpdate(query));
	}
	
	public abstract void test() throws Exception;
	
}
