package org.jsemantic.jembedded.services.dbservice;

import java.sql.ResultSet;

import org.jsemantic.jembedded.services.dbservice.exception.DBServerException;
import org.jsemantic.jembedded.services.dbservice.impl.DBServerConfiguration;

public interface DBServer {
	/**
	 * 
	 * @throws Throwable
	 */
	public void start() throws DBServerException;
	/**
	 * 
	 * @throws Throwable
	 */
	public void stop() throws DBServerException;
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
	
	public void setDbServerConfiguration(DBServerConfiguration dbServerConfiguration);
	
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
