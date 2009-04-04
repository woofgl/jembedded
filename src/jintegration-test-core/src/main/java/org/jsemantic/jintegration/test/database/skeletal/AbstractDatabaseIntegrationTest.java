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

import java.util.List;
import java.util.Map;

import org.jsemantic.jintegration.test.core.skeletal.AbstractIntegrationTest;
import org.jsemantic.jintegration.test.database.DatabaseIntegrationTest;
import org.jsemantic.services.hsqldb.DBServer;

public abstract class AbstractDatabaseIntegrationTest extends
		AbstractIntegrationTest implements DatabaseIntegrationTest {

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

	protected List<Map<String, Object>> executeQueryForList(String query,
			Object... args) {
		return getDBService().executeQueryForList(query, args);
	}

	protected Map<String, Object> executeQuery(String query, Object... args) {
		return getDBService().executeQuery(query, args);
	}

	protected int executeUpdate(String query, Object... args) {
		return getDBService().executeUpdate(query, args);
	}

	public void assertResultNotNull(String query, Object... args) {
		assertNotNull(executeQuery(query, args));
	}

	public void assertResultNull(String query, Object... args) {
		assertNull(executeQuery(query, args));
	}

	public void assertResultListNull(String query, Object... args) {
		assertNull(getDBService().executeQueryForList(query, args));
	}

	public void assertResultListNotNull(String query, Object... args) {
		assertNotNull(getDBService().executeQueryForList(query, args));
	}

	public void assertEqualsUpdateResult(String query, int result,
			Object... args) {
		assertEquals(result, executeUpdate(query, args));
	}
	
	public void assertFieldEquals(String query, String fieldName, Object expected, Object...args) {
		Map<String, Object> result = executeQuery(query, args);
		assertNotNull(result);
		
		Object field = result.get(fieldName.toUpperCase().trim());
		assertNotNull(field);
		assertEquals(expected, field);
	}
	
	public abstract void test() throws Exception;

}
