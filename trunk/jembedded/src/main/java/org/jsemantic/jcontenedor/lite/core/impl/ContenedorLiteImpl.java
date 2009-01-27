package org.jsemantic.jcontenedor.lite.core.impl;

import org.jsemantic.jcontenedor.lite.core.ContenedorLite;
import org.jsemantic.jcontenedor.lite.core.context.Context;
import org.jsemantic.services.core.Component;
import org.jsemantic.services.core.exception.ComponentException;
import org.jsemantic.services.core.service.Service;
import org.jsemantic.services.core.service.exception.ServiceException;
import org.jsemantic.services.core.service.skeletal.AbstractService;
import org.springframework.context.ApplicationContext;

public class ContenedorLiteImpl  implements ContenedorLite {

	private ApplicationContext applicationContext = null;

	private ContenedorLiteImpl(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public static ContenedorLite getInstance(
			ApplicationContext applicationContext) {
		return new ContenedorLiteImpl(applicationContext);
	}

	public Object getBean(String id) {
		if (applicationContext.containsBean(id)) {
			return applicationContext.getBean(id);
		}
		throw new RuntimeException("Bean " + id + "\\t not exist");
	}

	public Component getComponent(String id) throws ComponentException {
		if (applicationContext.containsBean(id)) {
			return (Component) applicationContext.getBean(id);
		}
		throw new ComponentException("Component " + id + "\\t not exist");
	}

	public Context getContext() {
		return null;
	}

	public Service getService(String id) throws ServiceException {
		if (applicationContext.containsBean(id)) {
			return (Service) applicationContext.getBean(id);
		}
		throw new ServiceException("Service " + id + "\\t not exist");
	
	}


	

}
