package org.jsemantic.jembedded.integration.spring;

import org.jsemantic.jembedded.integration.spring.skeletal.AbstractSpringMVCEmbeddTest;
import org.jsemantic.jembedded.services.httpservice.annotation.HttpServiceConfiguration;


@HttpServiceConfiguration(host="", port=9006, root="/test", webApplication="src/main/resources/webapp/spring")
public class SpringMVCEmbeddedTest extends AbstractSpringMVCEmbeddTest {
	
	
	@Override
	public void test() {
		super.assertNotNull(super.getWebApplicationContext());
		super.assertNotNullContextPath();
		super.assertEqualsContextPath("/test");
	}
	
	public void testClient() {
		String uri = "http://localhost:9006/test/controller";
		super.assertExistBeanInDispatcherServlet("controller");
		super.assertExistBeanInApplicationContext("service");
		
		super.assertResponseStatusOK(uri);
		super.assertResponseContentEmpty(uri);
	}
}
