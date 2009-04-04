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

import org.jsemantic.jiservice.core.annotation.AnnotationProcessor;
import org.jsemantic.jiservice.core.service.Service;
import org.jsemantic.services.jettyservice.JettyService;
import org.springframework.util.StringUtils;

public class HttpIntegrationTestAnnotationProcessor implements
		AnnotationProcessor {

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
				((JettyService) httpService).setRootContext(root);
			}

			if (StringUtils.hasText(src)) {
				((JettyService) httpService).setWebApplication(src);
			}
			if (port > 0) {
				((JettyService) httpService).setPort(port);
			}
		}
		return httpService;
	}

}
