package org.jsemantic.jembedded.integration.spring;

import org.jsemantic.jembedded.integration.http.HttpEmbeddedTest;

public interface SpringMVCEmbeddedTest extends HttpEmbeddedTest {
	
	void assertExistBeanInDispatcherServlet(String bean);

	void assertExistBeanInApplicationContext(String bean);
	
	Object getBussinessComponent(String id);
	
	Object getMVCComponent(String id);
	
}
