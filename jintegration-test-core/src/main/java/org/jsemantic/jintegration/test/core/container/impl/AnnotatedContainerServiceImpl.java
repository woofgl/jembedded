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
package org.jsemantic.jintegration.test.core.container.impl;

import java.util.Map;
import java.util.WeakHashMap;

import org.jsemantic.jcontenedor.JContenedor;
import org.jsemantic.jcontenedor.configuration.JContenedorFactory;
import org.jsemantic.jintegration.test.core.container.AnnotatedContainerService;
import org.jsemantic.jintegration.test.database.annotation.DatabaseIntegrationConfigurationTest;
import org.jsemantic.jintegration.test.http.annotation.HttpIntegrationConfigurationTest;
import org.jsemantic.jintegration.test.jms.annotation.JMSIntegrationConfigurationTest;
import org.jsemantic.jservice.core.annotation.AnnotationProcessor;
import org.jsemantic.jservice.core.component.Component;
import org.jsemantic.jservice.core.component.exception.ComponentException;
import org.jsemantic.jservice.core.exception.CycleException;
import org.jsemantic.jservice.core.service.Service;
import org.jsemantic.jservice.core.service.exception.ServiceException;
import org.jsemantic.jservice.core.service.skeletal.AbstractService;
import org.jsemantic.services.httpservice.HttpService;
import org.springframework.util.StringUtils;

public class AnnotatedContainerServiceImpl extends AbstractService implements
		AnnotatedContainerService {

	private final static String CONFIGURATION_FILE = "classpath:META-INF/jintegration-test/jintegration-test.xml";

	private JContenedor contenedor = null;

	private Map<String, Service> services = new WeakHashMap<String, Service>();

	private String configurationFile = null;

	private Class<?> annotatedClass = null;

	private AnnotatedContainerServiceImpl(Class<?> annotatedClass) {
		super();
		this.configurationFile = CONFIGURATION_FILE;
		this.annotatedClass = annotatedClass;
		super.init();
	}

	private AnnotatedContainerServiceImpl(String configurationFile,
			Class<?> annotatedClass) {
		super();
		this.configurationFile = configurationFile;
		if (!StringUtils.hasText(this.configurationFile)) {
			this.configurationFile = CONFIGURATION_FILE;
		}
		this.annotatedClass = annotatedClass;
		super.init();
	}

	public Service getService(String id) throws ServiceException {
		if (services.containsKey(id)) {
			return services.get(id);
		}
		throw new ServiceException("Service : " + id + " not found.");
	}

	public Component getComponent(String id) throws ComponentException {
		return this.contenedor.getComponent(id);
	}

	public static AnnotatedContainerServiceImpl getInstance(Class<?> service) {
		return new AnnotatedContainerServiceImpl(service);
	}

	public static AnnotatedContainerServiceImpl getInstance(
			String configurationFile, Class<?> service) {
		return new AnnotatedContainerServiceImpl(configurationFile, service);
	}

	@Override
	protected void stopService() throws ServiceException {
		this.stopServices();
	}

	@Override
	protected void startService() throws ServiceException {
		this.startServices();
	}

	@Override
	protected void postConstruct() throws CycleException {
		contenedor = JContenedorFactory.getInstance(CONFIGURATION_FILE);
		contenedor.start();
		this.processAnnotatedServices(this.annotatedClass);
	}

	@Override
	protected void release() throws CycleException {
		services.clear();
		services = null;
		contenedor.stop();
		contenedor.dispose();
		contenedor = null;
	}

	private void startServices() {
		for (String id : services.keySet()) {
			getService(id).start();
		}
	}

	private void stopServices() {
		for (String id : services.keySet()) {
			getService(id).stop();
		}
	}

	private void processAnnotatedServices(Class<?> testClass) {

		if (testClass
				.isAnnotationPresent(HttpIntegrationConfigurationTest.class)) {

			HttpService httpService = (HttpService) contenedor
					.getService(HttpService.HTTP_SERVICE_ID);
			if (httpService != null) {
				AnnotationProcessor processor = (AnnotationProcessor) contenedor
						.getBean("httpTestAnnotationProcessor");

				boolean processed = processor.processAnnotation(httpService,
						testClass);
				if (processed) {
					services.put(HttpService.HTTP_SERVICE_ID, httpService);
				}
			}
		}

		if (testClass
				.isAnnotationPresent(DatabaseIntegrationConfigurationTest.class)) {
			Service dbService = contenedor.getService("dbService");
			if (dbService != null) {
				AnnotationProcessor processor = (AnnotationProcessor) contenedor
						.getBean("databaseTestAnnotationProcessor");

				boolean processed = processor.processAnnotation(dbService,
						testClass);
				if (processed) {
					services.put("dbService", dbService);
				}
			}
		}
		
		if (testClass
				.isAnnotationPresent(JMSIntegrationConfigurationTest.class)) {
			Service jmsService = contenedor.getService("activeMQService");
			if (jmsService != null) {
				AnnotationProcessor processor = (AnnotationProcessor) contenedor
						.getBean("jmsTestAnnotationProcessor");

				boolean processed = processor.processAnnotation(jmsService,
						testClass);
				if (processed) {
					services.put("jmsService", jmsService);
				}
			}
		}
		

	}

}
