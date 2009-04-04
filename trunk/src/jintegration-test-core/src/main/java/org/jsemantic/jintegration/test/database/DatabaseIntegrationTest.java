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

/**
 * 
 * @author AEJ002ES
 *
 */
public interface DatabaseIntegrationTest {
	
	/**
	 * 
	 * @param query
	 * @param args
	 */
	public void assertResultListNull(String query, Object... args);
	/**
	 * 
	 * @param query
	 * @param args
	 */
	public void assertResultListNotNull(String query, Object... args);
	/**
	 * 
	 * @param query
	 */
	public void assertResultNotNull(String query, Object ...args);
	/**
	 * 
	 * @param query
	 */
	public void assertResultNull(String query, Object ...args);
	/**
	 * 
	 * @param query
	 * @param result
	 */
	public void assertEqualsUpdateResult(String query, int result, Object ...args);
	/**
	 * 
	 * @param query
	 * @param field
	 * @param expected
	 * @param args
	 */
	public void assertFieldEquals(String query, String fieldName, Object expected, Object...args);
}
