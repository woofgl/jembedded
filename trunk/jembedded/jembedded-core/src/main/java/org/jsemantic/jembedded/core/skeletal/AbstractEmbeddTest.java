package org.jsemantic.jembedded.core.skeletal;

import org.jsemantic.jembedded.core.EmbeddTest;
import org.jsemantic.jembedded.core.manager.ServiceManager;
import org.jsemantic.jservice.core.component.Component;
import org.jsemantic.jservice.core.service.Service;


import junit.framework.TestCase;

public abstract class AbstractEmbeddTest extends TestCase implements EmbeddTest {
	
	private ServiceManager serviceManager = null;

	public Service getService(String id) {
		return serviceManager.getService(id);
	}

	public Component getComponent(String id) {
		return serviceManager.getComponent(id);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		init();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		release();
	}

	protected void init() throws Exception {
		Class<?> testClass = getClass().getMethod("test", new Class[] {})
				.getDeclaringClass();
		serviceManager = ServiceManager.getInstance();
		serviceManager.processServices(testClass);
		serviceManager.startServices();
	}
	protected void release() throws Exception {
		serviceManager.stopServices();
		serviceManager.dispose();
	}
	
	

	public abstract void test() throws Exception;

}
