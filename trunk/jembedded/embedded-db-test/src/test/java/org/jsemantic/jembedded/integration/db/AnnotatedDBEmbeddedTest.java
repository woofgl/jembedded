package org.jsemantic.jembedded.integration.db;

import org.jsemantic.jembedded.integration.db.skeletal.AbstractDBEmbeddedTest;
import org.jsemantic.services.dbservice.annotation.*;

@DBService
@DBServiceConfiguration(dbPath = "mem:test", password = "", user = "")
public class AnnotatedDBEmbeddedTest extends AbstractDBEmbeddedTest {

	@Override
	public void test() throws Exception {
		createTable();
		super
				.assertEqualsUpdateResult(
						"insert into test values(1, 'prueba', 'descripcion de la prueba')",
						1);
		super.assertResulSetNotNull("select * from test");
	}

	public void test2() throws Exception {
		createTable();
		super
				.assertEqualsUpdateResult(
						"insert into test values(1, 'prueba', 'descripcion de la prueba')",
						1);
		super.assertResulSetNotNull("select * from test");
	}

	private void createTable() {
		super.executeUpdate("CREATE TABLE test" + "( 	id INTEGER IDENTITY, "
				+ "	nombre VARCHAR(25), " + "	descripcion VARCHAR(56))");
	}

}
