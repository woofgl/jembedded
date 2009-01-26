package org.jsemantic.jembedded.integration.db;

import java.sql.ResultSet;


public interface DBEmbeddTest {
	/**
	 * 
	 * @param query
	 * @return
	 */
	public ResultSet executeQuery(String query);
	/**
	 * 
	 * @param query
	 * @return
	 */
	public int executeUpdate(String query);
	/**
	 * 
	 * @param query
	 */
	public void assertResulSetNotNull(String query);
	/**
	 * 
	 * @param query
	 */
	public void assertResulSetNull(String query);
	/**
	 * 
	 * @param query
	 * @param result
	 */
	public void assertEqualsUpdateResult(String query, int result);
}
