package org.jsemantic.jembedded.core.skeletal;

import java.sql.ResultSet;
import java.util.Map;

import org.jsemantic.jembedded.core.EmbeddTest;
import org.jsemantic.jembedded.core.annotation.ServiceAnnotationProcessor;
import org.jsemantic.jembedded.core.container.Container;
import org.jsemantic.jembedded.integration.db.DBEmbeddTest;
import org.jsemantic.jembedded.services.dbservice.DBServer;
import org.jsemantic.services.core.service.Service;

import junit.framework.TestCase;

public abstract class AbstractEmbeddTest extends TestCase implements
		EmbeddTest, DBEmbeddTest {

	private Map<String, Service> services = null;

	public Service getService(String id) {
		return services.get(id);
	}

	public Object getComponent(String id) {
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
		Class<?> testClass = getClass().getMethod("test", new Class[] {})
				.getDeclaringClass();
		this.services = ServiceAnnotationProcessor.processAnnotation(testClass);

		for (String id : services.keySet()) {
			getService(id).start();
		}
	}

	protected void release() throws Exception {
		for (String id : services.keySet()) {
			getService(id).stop();
		}
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
