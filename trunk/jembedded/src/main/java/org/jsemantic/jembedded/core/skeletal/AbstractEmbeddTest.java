package org.jsemantic.jembedded.core.skeletal;

import org.jsemantic.jembedded.core.EmbeddTest;
import org.jsemantic.jembedded.core.container.Container;

import junit.framework.TestCase;

public abstract class AbstractEmbeddTest extends TestCase implements EmbeddTest {
	
	public Object getService(String id) {
		return Container.getService(id);
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
		Container.getInstance();
	}

	protected void release() throws Exception {
		
	}
	
	public abstract void test() throws Exception;
	
}
