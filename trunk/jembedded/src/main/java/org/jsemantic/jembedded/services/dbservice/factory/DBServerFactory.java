package org.jsemantic.jembedded.services.dbservice.factory;

import java.lang.annotation.Annotation;

import org.jsemantic.jembedded.services.dbservice.DBServer;
import org.jsemantic.jembedded.services.dbservice.annotation.DBServiceConfiguration;
import org.jsemantic.jembedded.services.dbservice.impl.DBServerConfiguration;
import org.jsemantic.jembedded.services.dbservice.impl.DBServerImpl;
import org.jsemantic.services.core.service.Service;
import org.springframework.util.StringUtils;

public class DBServerFactory {

	private DBServerFactory() {

	}

	public static DBServer getInstance() {
		DBServer dbServer = new DBServerImpl();
		return dbServer;
	}

	public static Service getInstance(Service dbServer, Annotation ann) {

		if (ann instanceof DBServiceConfiguration) {
			String dbPath = ((DBServiceConfiguration) ann).dbPath();
			String user = ((DBServiceConfiguration) ann).user();
			String password = ((DBServiceConfiguration) ann).password();
			boolean isMemoryModel = ((DBServiceConfiguration) ann)
					.isMemoryMode();

			DBServerConfiguration configuration = ((DBServer) dbServer)
					.getDbServerConfiguration();

			if (StringUtils.hasText(dbPath)) {
				configuration.setDbPath(dbPath);
			}

			if (StringUtils.hasText(user)) {
				configuration.setDbName(user);
			}

			if (StringUtils.hasText(password)) {
				configuration.setDbName(password);
			}

			((DBServerImpl) dbServer).setDbServerConfiguration(configuration);
			((DBServerImpl) dbServer).setMemoryDBServer(isMemoryModel);
		}

		return dbServer;
	}

	public static DBServer getInstance(DBServerConfiguration configuration,
			boolean dbFileMode) {
		DBServer dbServer = new DBServerImpl();
		((DBServerImpl) dbServer).setDbServerConfiguration(configuration);
		dbServer.setMemoryDBServer(!dbFileMode);
		return dbServer;
	}

}
