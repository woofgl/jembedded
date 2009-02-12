package org.jsemantic.jembedded.integration.db.skeletal;

import java.sql.ResultSet;

import org.jsemantic.jembedded.core.skeletal.AbstractEmbeddTest;
import org.jsemantic.jembedded.services.dbservice.DBServer;

public abstract class AbstractDBEmbeddedTest extends AbstractEmbeddTest { 
	
	protected void setUp() throws Exception {
		init();
	}

	protected void tearDown() throws Exception {
		release();
	}

	protected void init() throws Exception {
		super.init();
	}

	protected void release() throws Exception {
		super.release();
	}
	
	protected DBServer getDBService() {
		return ((DBServer) getService("dbService"));
	}

	public ResultSet executeQuery(String query) {
		return getDBService().executeQuery(query);
	}

	public int executeUpdate(String query) {
		return getDBService().executeUpdate(query);
	}

	public void assertResulSetNotNull(String query) {
		super.assertNotNull(executeQuery(query));
	}

	public void assertResulSetNull(String query) {
		super.assertNull(executeQuery(query));
	}

	public void assertEqualsUpdateResult(String query, int result) {
		super.assertEquals(result, executeUpdate(query));
	}
	
	public abstract void test() throws Exception;
	
}
