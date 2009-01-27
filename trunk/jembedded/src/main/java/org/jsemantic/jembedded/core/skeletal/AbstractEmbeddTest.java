package org.jsemantic.jembedded.core.skeletal;

import java.sql.ResultSet;
import org.jsemantic.jembedded.core.EmbeddTest;
import org.jsemantic.jembedded.core.manager.ServiceManager;
import org.jsemantic.jembedded.integration.db.DBEmbeddTest;
import org.jsemantic.jembedded.services.dbservice.DBServer;
import org.jsemantic.services.core.Component;
import org.jsemantic.services.core.service.Service;

import junit.framework.TestCase;

public abstract class AbstractEmbeddTest extends TestCase implements
		EmbeddTest, DBEmbeddTest {
	
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
		serviceManager.destroy();
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
