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
package org.jsemantic.jembedded.test.http;

import org.jsemantic.jintegration.test.http.annotation.HttpIntegrationConfigurationTest;
import org.jsemantic.jintegration.test.http.skeletal.AbstractIntegrationHttpTest;

@HttpIntegrationConfigurationTest(port=9006, root="/test", src="src/main/resources/webapp/test")
public class EmbeddedIntegrationHttpTest extends AbstractIntegrationHttpTest {
	
	public void test() {
		assertNotNull(getWebApplicationContext());
		assertNotNullContextPath();
		assertEqualsContextPath("/test");
	}

	public void testRequest() {
		String uri = "http://localhost:9006/test/index.html";
		assertResponseStatusOK(uri);
		assertContentNotEmpty(uri);
	}
	
	
}
