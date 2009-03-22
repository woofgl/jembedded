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
package org.jsemantic.jintegration.test.http.annotation;

import java.lang.annotation.Annotation;

import org.jsemantic.services.httpservice.HttpService;
import org.jsemantic.jservice.core.service.Service;
import org.jsemantic.jservice.core.annotation.AnnotationProcessor;
import org.springframework.util.StringUtils;

public class HttpIntegrationTestAnnotationProcessor implements
		AnnotationProcessor {

	/** The Constant log. */
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(HttpIntegrationTestAnnotationProcessor.class);

	public boolean processAnnotation(Service httpService, Class<?> service) {
		Annotation ann = service.getAnnotation(HttpIntegrationConfigurationTest.class);
		configureInstance(httpService, ann);
		return true;
	}

	private Service configureInstance(Service httpService, Annotation ann) {

		if (ann instanceof HttpIntegrationConfigurationTest) {

			String root = ((HttpIntegrationConfigurationTest) ann).root();
			String src = ((HttpIntegrationConfigurationTest) ann).src();
			int port = ((HttpIntegrationConfigurationTest) ann).port();

			if (StringUtils.hasText(root)) {
				((HttpService) httpService).setRootContext(root);
			}

			if (StringUtils.hasText(src)) {
				((HttpService) httpService).setWebApplication(src);
			}
			if (port > 0) {
				((HttpService) httpService).setPort(port);
			}

		}

		return httpService;
	}

}
