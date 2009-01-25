package org.jsemantic.jembedded.core;

import java.sql.ResultSet;

public interface SeverEmbeddTest extends EmbeddTest {
	
	ResultSet executeQuery(String query);

	int executeUpdate(String query);

	void assertResulSetNotNull(String query);

	void assertResulSetNull(String query);

	void assertEqualsUpdateResult(String query, int result);
}
