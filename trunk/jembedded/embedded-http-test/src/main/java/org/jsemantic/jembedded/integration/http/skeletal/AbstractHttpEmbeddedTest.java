package org.jsemantic.jembedded.integration.http.skeletal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.jsemantic.jembedded.core.skeletal.AbstractEmbeddTest;
import org.jsemantic.jembedded.integration.http.HttpEmbeddedTest;
import org.jsemantic.services.httpservice.HttpService;
import org.jsemantic.services.httpservice.client.HttpTestClient;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.util.StringUtils;

public abstract class AbstractHttpEmbeddedTest extends AbstractEmbeddTest
		implements HttpEmbeddedTest {

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

	protected HttpService getHttpService() {
		return ((HttpService) super.getService("httpService"));
	}

	public void assertEqualsContextPath(String contextPath) {
		super.assertEquals(getHttpService().getApplicationContext()
				.getContextPath(), contextPath);
	}

	public void assertNotNullContextPath() {
		super.assertNotNull(getHttpService().getApplicationContext()
				.getContextPath());
	}

	public void assertEqualsAttribute(String attribute, Object value) {
		super.assertEquals(getHttpService().getApplicationContext()
				.getAttribute(attribute), value);
	}

	public void assertInitParameterNotNull(String initParameter) {
		super.assertNotNull(getHttpService().getApplicationContext()
				.getInitParameter(initParameter));
	}

	public WebAppContext getWebApplicationContext() {
		return getHttpService().getApplicationContext();
	}

	public ServletContext getServletContext() {
		return ((HttpService) super.getService("httpService"))
				.getServerContext();
	}

	public void assertContentNotEmpty(String uri) {
		HttpResponse response = null;
		try {
			response = httpTestClient.executeHttpGetRequest(uri);
			super.assertTrue(response.getEntity().getContentLength() > 0);
			String content = getContent(response.getEntity().getContent())
					.toString();
			super.assertTrue(StringUtils.hasText(content));
		} catch (Throwable e) {
			super.fail(e.getMessage());
		} finally {
			httpTestClient.consumeContent(response);
		}
	}

	public void assertResponseContentEmpty(String uri) {
		HttpResponse response = null;
		try {
			response = httpTestClient.executeHttpGetRequest(uri);
			super.assertTrue(response.getEntity().getContentLength() == 0);
			String content = getContent(response.getEntity().getContent())
					.toString();
			super.assertFalse(StringUtils.hasText(content));
		} catch (Throwable e) {
			super.fail(e.getMessage());
		} finally {
			httpTestClient.consumeContent(response);
		}
	}

	public void assertResponseStatusOK(String uri) {
		HttpResponse response = null;
		try {
			response = httpTestClient.executeHttpGetRequest(uri);
			StatusLine status = response.getStatusLine();
			super.assertEquals(200, status.getStatusCode());
		} catch (Throwable e) {
			super.fail(e.getMessage());
			//httpTestClient.consumeContent(response);
		} finally {
			httpTestClient.consumeContent(response);
		}
	}

	public void assertResponseNotNull(String uri) {
		HttpResponse response = null;
		try {
			response = httpTestClient.executeHttpGetRequest(uri);
			super.assertNotNull(response);
		} catch (Throwable e) {
			super.fail(e.getMessage());
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
			super.fail(e.getMessage());
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
