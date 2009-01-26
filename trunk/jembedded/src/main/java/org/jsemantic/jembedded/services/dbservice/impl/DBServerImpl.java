/**
 * 
 */
package org.jsemantic.jembedded.services.dbservice.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jsemantic.jembedded.services.dbservice.DBServer;
import org.jsemantic.jembedded.services.dbservice.exception.DBServerException;
import org.jsemantic.services.core.service.skeletal.AbstractService;

public class DBServerImpl extends AbstractService implements DBServer {

	private Connection connection;

	private boolean isMemoryDBServer = true;

	private DBServerConfiguration dbServerConfiguration = null;

	public DBServerConfiguration getDbServerConfiguration() {
		return dbServerConfiguration;
	}

	public void setDbServerConfiguration(DBServerConfiguration dbServerConfiguration) {
		this.dbServerConfiguration = dbServerConfiguration;
	}

	public DBServerImpl() {

	}
	
	public void init() {
	}
	
	public void run() throws DBServerException {
		this.connection = getConnection();
	}
	
	public void pause() throws DBServerException  {
		dispose();
	}
	
	public void dispose() throws DBServerException {
		try {
			this.executeQuery("SHUTDOWN");
			if (!this.isMemoryDBServer) {
				this.deleteFile(this.dbServerConfiguration.getFile()
						+ ".properties");
				this.deleteFile(this.dbServerConfiguration.getFile()
						+ ".script");
			}

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
			}
		}
	}

	public ResultSet executeQuery(String query) throws DBServerException {
		ResultSet resultSet = null;
		Statement st = null;
		try {
			st = connection.createStatement();
			resultSet = st.executeQuery(query);
			return resultSet;
		} catch (Throwable e) {
			throw new DBServerException(e.getMessage(), e);
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
	}

	public int executeUpdate(String query) throws DBServerException {
		Statement st = null;
		int returnValue = -1;
		try {
			st = connection.createStatement();
			returnValue = st.executeUpdate(query);
		} catch (Throwable e) {
			throw new DBServerException(e.getMessage(), e);
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
		return returnValue;
	}

	public boolean isMemoryDBServer() {
		return isMemoryDBServer;
	}

	public void setMemoryDBServer(boolean isMemoryDBServer) {
		this.isMemoryDBServer = isMemoryDBServer;
	}

	private Connection getConnection() throws DBServerException {
		try {
			Class.forName(this.dbServerConfiguration.getDriverClass())
					.newInstance();
			if (this.isMemoryDBServer) {
				connection = DriverManager.getConnection("jdbc:hsqldb:"
						+ this.dbServerConfiguration.getDbPath(),
						this.dbServerConfiguration.getUser(),
						this.dbServerConfiguration.getPassword());
			} else {
				connection = DriverManager.getConnection("jdbc:hsqldb:"
						+ this.dbServerConfiguration.getFile(),
						this.dbServerConfiguration.getUser(),
						this.dbServerConfiguration.getPassword());
			}
			return connection;
		} catch (Throwable e) {
			throw new DBServerException(e.getMessage(), e);
		}
	}

	private void deleteFile(String file) {
		File f = new File(file);
		if (f.exists()) {
			f.delete();
		}
	}


}
