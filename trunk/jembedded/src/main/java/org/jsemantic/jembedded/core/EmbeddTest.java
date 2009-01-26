package org.jsemantic.jembedded.core;

public interface EmbeddTest {
	/**
	 * 
	 * @param id
	 * @return
	 */
	Object getService(String id);
	/**
	 * 
	 * @throws Exception
	 */
	void test() throws Exception;
}
