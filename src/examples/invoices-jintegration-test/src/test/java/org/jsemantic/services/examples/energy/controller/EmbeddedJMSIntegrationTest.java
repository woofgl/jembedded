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
package org.jsemantic.services.examples.energy.controller;

import org.jsemantic.jintegration.test.jms.annotation.JMSIntegrationConfigurationTest;
import org.jsemantic.jintegration.test.jms.skeletal.AbstractJMSIntegrationTest;

@JMSIntegrationConfigurationTest(connector = "tcp://localhost:61666", jmx = "false")
public class EmbeddedJMSIntegrationTest extends AbstractJMSIntegrationTest {

	public void test() {
		testDestinationCreation("TEST.TEST", "tcp://localhost:61666");		
	}
}
