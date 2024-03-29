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
package org.jsemantic.jintegration.test.core.skeletal;

import org.jsemantic.jintegration.test.core.IntegrationTest;
import org.jsemantic.jintegration.test.core.container.impl.AnnotatedContainerServiceImpl;
import org.jsemantic.jiservice.core.component.Component;
import org.jsemantic.jiservice.core.service.Service;

import junit.framework.TestCase;

public abstract class AbstractIntegrationTest extends TestCase implements IntegrationTest {
	
	private AnnotatedContainerServiceImpl container = null;
	
	public Service getService(String id) {
		return container.getService(id);
	}

	public Component getComponent(String id) {
		return container.getComponent(id);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		init();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		release();
	}

	protected void init() throws Exception {
		Class<?> testClass = getClass().getMethod("test", new Class[] {})
				.getDeclaringClass();
		container = AnnotatedContainerServiceImpl.getInstance(testClass);
		container.start();
	}
	protected void release() throws Exception {
		container.stop();
		container.dispose();
	}
	
	public abstract void test() throws Exception;

}
