package org.jsemantic.jembedded.integration.dbservice;

import org.jsemantic.jembedded.core.skeletal.AbstractEmbeddTest;
import org.jsemantic.jembedded.services.dbservice.annotation.DBService;

@DBService
public class DBServiceEmbbedTest extends AbstractEmbeddTest {
	
	@Override
	public void test() throws Exception {
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