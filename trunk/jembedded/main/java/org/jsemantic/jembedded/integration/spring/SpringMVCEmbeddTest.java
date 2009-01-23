package org.jsemantic.jembedded.integration.spring;

import org.jsemantic.jembedded.integration.http.HttpEmbeddTest;

public interface SpringMVCEmbeddTest extends HttpEmbeddTest {
	
	void assertExistBeanInDispatcherServlet(String bean);

	void assertExistBeanInApplicationContext(String bean);
	
	Object getBussinessComponent(String id);
	
	Object getMVCComponent(String id);
	
}
