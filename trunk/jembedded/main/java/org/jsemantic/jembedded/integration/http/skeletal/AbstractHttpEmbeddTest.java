package org.jsemantic.jembedded.integration.http.skeletal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.jsemantic.jembedded.core.skeletal.AbstractServerEmbeddTest;
import org.jsemantic.jembedded.integration.http.HttpEmbeddTest;
import org.jsemantic.jembedded.services.httpservice.HttpService;
import org.jsemantic.jembedded.services.httpservice.annotation.HttpServiceAnnotationProcessor;
import org.jsemantic.jembedded.services.httpservice.client.HttpTestClient;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.util.StringUtils;

public abstract class AbstractHttpEmbeddTest extends AbstractServerEmbeddTest
		implements HttpEmbeddTest {

	private HttpService httpService = null;

	public HttpService getHttpService() {
		return httpService;
	}

	private HttpTestClient httpTestClient = null;
	
	protected void setUp() throws Exception {
		init();
	}

	protected void tearDown() throws Exception {
		release();
	}
	
	protected void init() throws Exception {
		super.init();
		Class<?> serviceClass = getClass().getMethod("test", new Class[] {})
		.getDeclaringClass();

		httpService = HttpServiceAnnotationProcessor
				.processAnnotation(serviceClass);

		httpTestClient = (HttpTestClient) super.getService("httpClient");
		this.httpService.start();
	}

	protected void release() throws Exception {
		super.release();
		httpService.stop();
		httpService.dispose();
		httpService = null;
	}

	public void assertEqualsContextPath(String contextPath) {
		super.assertEquals(
				httpService.getApplicationContext().getContextPath(),
				contextPath);
	}

	public void assertNotNullContextPath() {
		super.assertNotNull(httpService.getApplicationContext()
				.getContextPath());
	}

	public void assertEqualsAttribute(String attribute, Object value) {
		super.assertEquals(httpService.getApplicationContext().getAttribute(
				attribute), value);
	}

	public void assertInitParameterNotNull(String initParameter) {
		super.assertNotNull(httpService.getApplicationContext()
				.getInitParameter(initParameter));
	}

	public WebAppContext getWebApplicationContext() {
		return httpService.getApplicationContext();
	}

	public ServletContext getServletContext() {
		return httpService.getServerContext();
	}

	public void assertContentNotEmpty(String uri) {
		HttpResponse response = null;
		try {
			response = httpTestClient.executeHttpGetRequest(uri);
			super.assertTrue(response.getEntity().getContentLength() > 0);
			String content = getContent(response.getEntity().getContent());
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
			String content = getContent(response.getEntity().getContent());
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
			httpTestClient.consumeContent(response);
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

	private String getContent(InputStream stream) throws IOException {
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
		return content.toString();
	}

}
