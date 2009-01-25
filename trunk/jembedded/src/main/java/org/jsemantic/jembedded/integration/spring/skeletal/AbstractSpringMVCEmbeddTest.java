package org.jsemantic.jembedded.integration.spring.skeletal;

import org.jsemantic.jembedded.integration.http.skeletal.AbstractHttpEmbeddTest;
import org.jsemantic.jembedded.integration.spring.SpringMVCEmbeddTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public abstract class AbstractSpringMVCEmbeddTest extends AbstractHttpEmbeddTest implements SpringMVCEmbeddTest {

	private static final String CONTEXT_DISPATCHER = "org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher";

	public void assertExistBeanInDispatcherServlet(String bean) {
		super.assertTrue(getDispatcherApplicationContext().containsBean(bean));
	}

	public void assertExistBeanInApplicationContext(String bean) {
		super.assertTrue(getSpringApplicationContext().containsBean(bean));
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
