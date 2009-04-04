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
package org.jsemantic.jintegration.test.spring.skeletal;

import org.jsemantic.jintegration.test.http.skeletal.AbstractIntegrationHttpTest;
import org.jsemantic.jintegration.test.spring.SpringMVCIntegrationTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public abstract class AbstractIntegrationSpringMVCTest extends
		AbstractIntegrationHttpTest implements SpringMVCIntegrationTest {

	private static final String CONTEXT_DISPATCHER = "org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher";

	public void assertExistBeanInDispatcherServlet(String bean) {
		assertTrue(getDispatcherApplicationContext().containsBean(bean));
	}

	public void assertExistBeanInApplicationContext(String bean) {
		assertTrue(getSpringApplicationContext().containsBean(bean));
	}

	private ApplicationContext getSpringApplicationContext() {
		return WebApplicationContextUtils.getWebApplicationContext(getHttpService()
				.getServerContext());
	}

	private ApplicationContext getDispatcherApplicationContext() {
		return (ApplicationContext) getHttpService().getServerContext()
				.getAttribute(CONTEXT_DISPATCHER);
	}
	
	public Object getBussinessComponent(String id) {
		return this.getSpringApplicationContext().getBean(id);
	}
	
	public Object getMVCComponent(String id) {
		return this.getDispatcherApplicationContext().getBean(id);
	}
	
	public abstract void test() throws Exception;

}
