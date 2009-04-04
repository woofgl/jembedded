/* Adolfo Estevez Jimenez: aestevezjimenez@gmail.com
 * Copyright 2007-2009
 * http://code.google.com/p/jcontenedor,
 * http://code.google.com/p/jembedded,
 * http://code.google.com/p/jservicerules
 * http://semanticj2ee.blogspot.com/ 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jsemantic.jintegration.test.database.annotation;

import java.lang.annotation.Annotation;

import org.jsemantic.jiservice.core.annotation.AnnotationProcessor;
import org.jsemantic.jiservice.core.service.Service;
import org.jsemantic.services.hsqldb.DBServer;
import org.jsemantic.services.hsqldb.impl.DBServerConfiguration;
import org.jsemantic.services.hsqldb.impl.DBServerImpl;
import org.springframework.util.StringUtils;

public class DatabaseIntegrationTestAnnotationProcessor implements
		AnnotationProcessor {

	public boolean processAnnotation(Service dbService, Class<?> service) {
		Annotation ann = service
				.getAnnotation(DatabaseIntegrationConfigurationTest.class);
		configureInstance(dbService, ann);
		return true;
	}

	private Service configureInstance(Service dbServer, Annotation ann) {

		String dbPath = ((DatabaseIntegrationConfigurationTest) ann)
				.databaseURI();
		String user = ((DatabaseIntegrationConfigurationTest) ann).user();
		String password = ((DatabaseIntegrationConfigurationTest) ann)
				.password();
		boolean isMemoryModel = ((DatabaseIntegrationConfigurationTest) ann)
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

		return dbServer;
	}

}
