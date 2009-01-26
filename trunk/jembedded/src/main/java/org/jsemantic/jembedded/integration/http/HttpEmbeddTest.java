package org.jsemantic.jembedded.integration.http;

import javax.servlet.ServletContext;

import org.mortbay.jetty.webapp.WebAppContext;

public interface HttpEmbeddTest {
	
	void assertEqualsContextPath(String contextPath);
	
	void assertNotNullContextPath();
	
	void assertEqualsAttribute(String attribute, Object value);
	
	void assertInitParameterNotNull(String initParameter);
	
	void assertContentNotEmpty(String uri);
	
	void assertResponseContentEmpty(String uri);
	
	void assertResponseStatusOK(String uri);
	
	void assertResponseNotNull(String uri);
	
	WebAppContext getWebApplicationContext();
	
	ServletContext getServletContext();
	
}
