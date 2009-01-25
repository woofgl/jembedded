package org.jsemantic.jembedded.integration.dbservice;

import org.jsemantic.jembedded.core.skeletal.AbstractServerEmbeddTest;
import org.jsemantic.jembedded.services.dbservice.annotation.DBService;
import org.jsemantic.jembedded.services.dbservice.annotation.DBServiceConfiguration;

@DBService
@DBServiceConfiguration(dbPath="mem:test", password = "", user = "")
public class AnnotatedDBServiceEmbeddTest extends AbstractServerEmbeddTest {

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
