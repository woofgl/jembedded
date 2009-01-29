package org.jsemantic.jembedded.core.manager;

import java.util.Map;

import org.jsemantic.jcontenedor.lite.ContenedorLite;
import org.jsemantic.jcontenedor.lite.configuration.ContenedorLiteFactory;
import org.jsemantic.jembedded.core.annotation.ServiceAnnotationProcessor;
import org.jsemantic.services.core.Component;
import org.jsemantic.services.core.exception.ComponentException;
import org.jsemantic.services.core.service.Service;
import org.jsemantic.services.core.skeletal.AbstractComponent;

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
	public void dispose() throws ComponentException {
		services.clear();
		services = null;
	}

	@Override
	public void init() throws ComponentException {

	}

	public void processServices(Class<?> testClass) throws Exception {
		this.services = ServiceAnnotationProcessor.processAnnotation(
				contenedor, testClass);
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
