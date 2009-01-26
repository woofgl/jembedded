package org.jsemantic.jembedded.core;


import org.jsemantic.jembedded.services.httpservice.HttpService;
import org.jsemantic.jembedded.services.httpservice.impl.HttpServiceImpl;

import junit.framework.TestCase;

public class HttpServiceTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void test() throws Exception {
		HttpService server = null;
		try {
			server =  new HttpServiceImpl();
			((HttpServiceImpl)server).init();
			super.assertNotNull(server);
			server.start();
		}
		catch (Throwable e) {
			super.fail(e.getMessage());
		}
		finally {
			server.stop();
			server = null;
		}
	}
	
}
