/*
 * Copyright 2007-2008, www.jsemantic.org, www.adolfoestevez.com,
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
package org.jsemantic.jembedded.services.dbservice.annotation;

import java.lang.annotation.Annotation;

import org.jsemantic.jcontenedor.lite.ContenedorLite;
import org.jsemantic.jembedded.services.dbservice.factory.DBServerFactory;
import org.jsemantic.services.core.service.Service;

public class DBServiceAnnotationProcessor {

	/** The Constant log. */
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(DBServiceAnnotationProcessor.class);

	@SuppressWarnings("unchecked")
	public static Service processAnnotation(ContenedorLite contenedor, Class service) {
		
		if (service.isAnnotationPresent(DBService.class)) {
			Service dbServer = contenedor.getService("dbService");
			if (service.isAnnotationPresent(DBServiceConfiguration.class)) {
				Annotation ann = service.getAnnotation(DBServiceConfiguration.class);
				return DBServerFactory.getInstance(dbServer, ann);
			}
			return dbServer;
		}
		return null;
	}

}
