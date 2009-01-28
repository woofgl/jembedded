package org.jsemantic.jcontenedor.lite.registry;

import java.util.Map;

import org.jsemantic.jcontenedor.lite.layer.Layer;
import org.jsemantic.services.core.context.Context;
import org.jsemantic.services.core.exception.ComponentException;
import org.jsemantic.services.core.service.Service;
import org.jsemantic.services.core.skeletal.AbstractComponent;

public class ServiceRegistryImpl extends AbstractComponent implements
		ServiceRegistry {

	private Layer registry = null;

	public ServiceRegistryImpl(Layer registry) {
		this.registry = registry;
	}

	public void setRegistry(Layer registry) {
		this.registry = registry;
	}

	public Service getService(String serviceName) {
		return (Service) registry.getComponent(serviceName);
	}

	public Service getService(String serviceName, Class<?> serviceClazz) {
		return (Service) registry.getComponent(serviceName, serviceClazz);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Service> getServices() {
		return (Map<String, Service>) registry
				.getComponentsOfType(Service.class);
	}

	public void dispose() throws ComponentException {
		// TODO Auto-generated method stub

	}

	public void init() throws ComponentException {
		// TODO Auto-generated method stub

	}

	public void setContainerContext(Context contenedorContext) {
		// TODO Auto-generated method stub
		Map<String, Service> services = getServices();

		for (String serviceId : services.keySet()) {
			Service service = services.get(serviceId);
			service.setContext(contenedorContext);
		}
	}

}
