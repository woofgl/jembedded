package org.jsemantic.jembedded.integration.http;

import org.jsemantic.jembedded.integration.http.skeletal.AbstractHttpEmbeddedTest;
import org.jsemantic.services.httpservice.annotation.HttpService;
import org.jsemantic.services.httpservice.annotation.HttpServiceConfiguration;

@HttpService
@HttpServiceConfiguration(host="", port=9006, root="/test", webApplication="src/main/resources/webapp/test")
public class HttpServiceEmbeddedTest extends AbstractHttpEmbeddedTest {
	
	public void test() {
		super.assertNotNull(super.getWebApplicationContext());
		super.assertNotNullContextPath();
		super.assertEqualsContextPath("/test");
	}

	public void testRequest() {
		String uri = "http://localhost:9006/test/index.html";
		super.assertResponseStatusOK(uri);
		super.assertContentNotEmpty(uri);
	}
	
	
}
