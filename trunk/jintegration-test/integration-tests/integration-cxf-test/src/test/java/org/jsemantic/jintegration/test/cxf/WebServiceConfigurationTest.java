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
package org.jsemantic.jintegration.test.cxf;

import org.jsemantic.jintegration.test.cxf.skeletal.AbstractCXFIntegrationTest;
import org.jsemantic.jintegration.test.http.annotation.HttpIntegrationConfigurationTest;

@HttpIntegrationConfigurationTest(port = 9005, root = "/", src = "src/main/resources/webapp/webservices/")
public class WebServiceConfigurationTest extends AbstractCXFIntegrationTest {

	@Override
	public void test() throws Exception {
		String wsdlURI = "http://localhost:9005/cxf/test?wsdl";
		String serviceURI = "http://localhost:9005/cxf/test/test";
		super.assertResponseStatusOK(wsdlURI);
		super.assertResponseNotNull(serviceURI);
	}

}
