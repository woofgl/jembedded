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
package org.jsemantic.jintegration.test.http.skeletal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.jsemantic.jintegration.test.core.skeletal.AbstractIntegrationTest;
import org.jsemantic.jintegration.test.http.HttpIntegrationTest;
import org.jsemantic.services.jettyservice.JettyService;
import org.jsemantic.services.jettyservice.client.HttpTestClient;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.util.StringUtils;

public abstract class AbstractIntegrationHttpTest extends
		AbstractIntegrationTest implements HttpIntegrationTest {

	private HttpTestClient httpTestClient = null;

	protected void setUp() throws Exception {
		init();
	}

	protected void tearDown() throws Exception {
		release();
	}

	protected void init() throws Exception {
		super.init();
		httpTestClient = (HttpTestClient) super.getComponent("httpClient");
	}

	protected void release() throws Exception {
		super.release();
	}

	protected JettyService getHttpService() {
		return ((JettyService) super.getService(JettyService.HTTP_SERVICE_ID));
	}

	public void assertEqualsContextPath(String contextPath) {
		assertEquals(getHttpService().getApplicationContext().getContextPath(),
				contextPath);
	}

	public void assertNotNullContextPath() {
		assertNotNull(getHttpService().getApplicationContext().getContextPath());
	}

	public void assertEqualsAttribute(String attribute, Object value) {
		assertEquals(getHttpService().getApplicationContext().getAttribute(
				attribute), value);
	}

	public void assertInitParameterNotNull(String initParameter) {
		assertNotNull(getHttpService().getApplicationContext()
				.getInitParameter(initParameter));
	}

	public WebAppContext getWebApplicationContext() {
		return getHttpService().getApplicationContext();
	}

	public ServletContext getServletContext() {
		return ((JettyService) super.getService(JettyService.HTTP_SERVICE_ID))
				.getServerContext();
	}

	public void assertContentNotEmpty(String uri) {
		HttpResponse response = null;
		try {
			response = httpTestClient.executeHttpGetRequest(uri);
			assertTrue(response.getEntity().getContentLength() > 0);
			String content = getContent(response.getEntity().getContent())
					.toString();
			assertTrue(StringUtils.hasText(content));
		} catch (Throwable e) {
			fail(e.getMessage());
		} finally {
			httpTestClient.consumeContent(response);
		}
	}

	public void assertResponseContentEmpty(String uri) {
		HttpResponse response = null;
		try {
			response = httpTestClient.executeHttpGetRequest(uri);
			assertTrue(response.getEntity().getContentLength() == 0);
			String content = getContent(response.getEntity().getContent())
					.toString();
			assertFalse(StringUtils.hasText(content));
		} catch (Throwable e) {
			fail(e.getMessage());
		} finally {
			httpTestClient.consumeContent(response);
		}
	}

	public void assertResponseStatusOK(String uri) {
		HttpResponse response = null;
		try {
			response = httpTestClient.executeHttpGetRequest(uri);
			StatusLine status = response.getStatusLine();
			assertEquals(200, status.getStatusCode());
		} catch (Throwable e) {
			fail(e.getMessage());
			// httpTestClient.consumeContent(response);
		} finally {
			httpTestClient.consumeContent(response);
		}
	}

	public void assertResponseNotNull(String uri) {
		HttpResponse response = null;
		try {
			response = httpTestClient.executeHttpGetRequest(uri);
			assertNotNull(response);
		} catch (Throwable e) {
			fail(e.getMessage());
		} finally {
			httpTestClient.consumeContent(response);
		}
	}

	public StringBuffer getResponse(String uri) {
		HttpResponse response = null;
		StringBuffer content = null;
		try {
			response = httpTestClient.executeHttpGetRequest(uri);
			content = getContent(response.getEntity().getContent());
		} catch (Throwable e) {
			fail(e.getMessage());
		} finally {
			httpTestClient.consumeContent(response);
		}
		return content;
	}

	private StringBuffer getContent(InputStream stream) throws IOException {
		StringBuffer content = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));

		while (true) {
			String line = in.readLine();
			if (line != null) {
				content.append(line + "\n");
			} else {
				break;
			}
		}
		return content;
	}

}
