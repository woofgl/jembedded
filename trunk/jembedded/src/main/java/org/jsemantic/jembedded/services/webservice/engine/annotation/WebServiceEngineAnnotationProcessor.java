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
package org.jsemantic.jembedded.services.webservice.engine.annotation;

import org.jsemantic.jcontenedor.lite.ContenedorLite;
import org.jsemantic.services.core.service.Service;

public class WebServiceEngineAnnotationProcessor {
	
	private WebServiceEngineAnnotationProcessor() {
		
	}
	
	/** The Constant log. */
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(WebServiceEngineAnnotationProcessor.class);

	@SuppressWarnings("unchecked")
	public static Service processAnnotation(ContenedorLite contenedor, Class service) {
		Service webHttpService = null;
		if (service.isAnnotationPresent(WebServiceEngine.class)) {
			webHttpService =  contenedor
			.getService("webHttpService");
		}
		return webHttpService;
	}

}
