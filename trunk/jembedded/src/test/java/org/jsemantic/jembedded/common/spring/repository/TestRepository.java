package org.jsemantic.jembedded.common.spring.repository;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class TestRepository extends JdbcDaoSupport {

	public void createTestTable() {
		super.getJdbcTemplate()
				.execute(
						"CREATE TABLE test" + "( 	id INTEGER IDENTITY, "
								+ "	nombre VARCHAR(25), "
								+ "	descripcion VARCHAR(56))");
	}

	public int insertTestRow() {
		return super
				.getJdbcTemplate()
				.update(
						"insert into test values(1, 'prueba', 'descripcion de la prueba')");
	}
	
	@SuppressWarnings("unchecked")
	public List getTestRows() {
		return super.getJdbcTemplate().queryForList("select * from test");
	}

}
