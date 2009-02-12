package org.jsemantic.jembedded.integration.http;

import org.jsemantic.jembedded.integration.http.skeletal.AbstractHttpEmbeddTest;
import org.jsemantic.jembedded.services.httpservice.annotation.HttpService;
import org.jsemantic.jembedded.services.httpservice.annotation.HttpServiceConfiguration;

@HttpService
@HttpServiceConfiguration(host="", port=9006, root="/test", webApplication="src/main/resources/webapp/test")
public class HttpServiceEmbeddTest extends AbstractHttpEmbeddTest {
	
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
