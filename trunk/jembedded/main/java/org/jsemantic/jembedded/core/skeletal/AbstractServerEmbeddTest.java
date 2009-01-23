package org.jsemantic.jembedded.core.skeletal;

import java.sql.ResultSet;

import org.jsemantic.jembedded.core.SeverEmbeddTest;
import org.jsemantic.jembedded.services.dbservice.DBServer;
import org.jsemantic.jembedded.services.dbservice.annotation.DBServiceAnnotationProcessor;

public abstract class AbstractServerEmbeddTest extends AbstractEmbeddTest
		implements SeverEmbeddTest {

	private DBServer dbServer = null;

	protected void setUp() throws Exception {
		init();
	}

	protected void tearDown() throws Exception {
		release();
	}

	protected void init() throws Exception {
		super.init();
		Class<?> testClass = getClass().getMethod("test", new Class[] {})
				.getDeclaringClass();
		dbServer = DBServiceAnnotationProcessor.processAnnotation(testClass);
		if (dbServer != null) {
			dbServer.start();
		}
	}

	protected void release() throws Exception {
		super.release();
		if (dbServer != null) {
			dbServer.stop();
			dbServer = null;
		}
	}

	public ResultSet executeQuery(String query) {
		return this.dbServer.executeQuery(query);
	}

	public int executeUpdate(String query) {
		return this.dbServer.executeUpdate(query);
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
}
