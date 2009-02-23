package org.jsemantic.jembedded.core.manager;

import java.util.Map;

import org.jsemantic.jcontenedor.core.ContenedorLite;
import org.jsemantic.jcontenedor.core.configuration.ContenedorLiteFactory;
import org.jsemantic.jembedded.core.annotation.ServiceAnnotationProcessor;
import org.jsemantic.jservice.core.component.Component;
import org.jsemantic.jservice.core.component.exception.ComponentException;
import org.jsemantic.jservice.core.component.skeletal.AbstractComponent;
import org.jsemantic.jservice.core.service.Service;

public class ServiceManager extends AbstractComponent implements Component {
	public final static String CONFIGURATION_FILE = "classpath:META-INF/jembedded/contenedor-configuration.xml";

	private static ContenedorLite contenedor = null;
	static {
		contenedor = ContenedorLiteFactory.getInstance(CONFIGURATION_FILE);
		contenedor.start();
	}

	private Map<String, Service> services = null;

	private ServiceManager() {
		init();
	}

	public static ServiceManager getInstance() {
		ServiceManager serviceManager = new ServiceManager();
		return serviceManager;
	}

	@Override
	protected void release() throws ComponentException {
		services.clear();
		services = null;
	}

	@Override
	protected void postConstruct() throws ComponentException {
		/*
		Service httpService = contenedor.getService("httpService");
		if (httpService != null)
			services.put(httpService.getId(), httpService);

		Service dbService = contenedor.getService("dbService");
		if (dbService != null)
			services.put(dbService.getId(), dbService);

		Service webService = contenedor.getService("webHttpService");
		if (webService != null)
			services.put(webService.getId(), webService);

		Service container = contenedor.getService("container");
		if (container != null)
			services.put(container.getId(), container);
			*/
	}

	public void processServices(Class<?> testClass) throws Exception {
		this.services = ServiceAnnotationProcessor.processAnnotation(contenedor,
				testClass);
	}

	public void startServices() {
		for (String id : services.keySet()) {
			getService(id).start();
		}
	}

	public void stopServices() {
		for (String id : services.keySet()) {
			getService(id).stop();
		}
	}

	public Service getService(String id) {
		return services.get(id);
	}
	
	public Component getComponent(String id) {
		return contenedor.getComponent(id);
	}

}
