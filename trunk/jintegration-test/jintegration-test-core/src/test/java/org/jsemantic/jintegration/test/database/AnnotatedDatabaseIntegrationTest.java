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
package org.jsemantic.jintegration.test.database;

import org.jsemantic.jintegration.test.database.annotation.DatabaseIntegrationConfigurationTest;
import org.jsemantic.jintegration.test.database.skeletal.AbstractDatabaseIntegrationTest;

@DatabaseIntegrationConfigurationTest(databaseURI = "jdbc:hsqldb:mem:test", password = "", user = "sa")
public class AnnotatedDatabaseIntegrationTest extends AbstractDatabaseIntegrationTest {

	@Override
	public void test() throws Exception {
		createTable();
		assertEqualsUpdateResult(
						"insert into test values(1, 'prueba', 'descripcion de la prueba')",
						1);
		assertResultNotNull("select * from test");
	}
	
	public void testAssertResultList() throws Exception {
		createTable();
		executeUpdate("insert into test values(1, 'prueba', 'descripcion de la prueba')");
		executeUpdate("insert into test values(2, 'prueba2', 'descripcion de la prueba2')");
		executeUpdate("insert into test values(3, 'prueba3', 'descripcion de la prueba3')");
		
		assertResultListNotNull("select * from test");
	}
	
	public void testFieldEquals() throws Exception {
		createTable();
		executeUpdate("insert into test values(1, 'prueba', 'descripcion de la prueba')");
		executeUpdate("insert into test values(2, 'prueba2', 'descripcion de la prueba2')");
		executeUpdate("insert into test values(3, 'prueba3', 'descripcion de la prueba3')");
			
		assertFieldEquals("select * from test where id=1", "nombre", "prueba");
	
	}

	private void createTable() {
		executeUpdate("CREATE TABLE test" + "( 	id INTEGER IDENTITY, "
				+ "	nombre VARCHAR(25), " + "	descripcion VARCHAR(56))");
	}


}
