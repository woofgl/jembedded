package org.jsemantic.jembedded.services.dbservice;

import java.sql.ResultSet;

import org.jsemantic.jembedded.services.dbservice.exception.DBServerException;
import org.jsemantic.jembedded.services.dbservice.impl.DBServerConfiguration;
import org.jsemantic.jservice.core.service.Service;

public interface DBServer extends Service {
	/**
	 * 
	 * @param isMemoryDBServer
	 */
	public void setMemoryDBServer(boolean isMemoryDBServer);

	/**
	 * 
	 * @return
	 */
	public boolean isMemoryDBServer();
	/**
	 * 
	 * @param dbServerConfiguration
	 */
	public void setDbServerConfiguration(
			DBServerConfiguration dbServerConfiguration);
	/**
	 * 
	 * @return
	 */
	public DBServerConfiguration getDbServerConfiguration();

	/**
	 * 
	 * @param pExpression
	 * @return
	 * @throws Throwable
	 */
	public ResultSet executeQuery(String pExpression) throws DBServerException;;

	/**
	 * 
	 * @param query
	 * @return
	 * @throws Throwable
	 */
	public int executeUpdate(String query) throws DBServerException;;

}
