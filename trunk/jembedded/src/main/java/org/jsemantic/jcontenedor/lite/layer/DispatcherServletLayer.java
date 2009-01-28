package org.jsemantic.jcontenedor.lite.layer;

import java.util.Map;

import javax.servlet.ServletContext;

import org.jsemantic.jcontenedor.lite.utils.ContainerUtils;
import org.jsemantic.services.core.context.Context;
import org.jsemantic.services.core.exception.ComponentException;
import org.jsemantic.services.core.skeletal.AbstractComponent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class DispatcherServletLayer extends AbstractComponent implements Layer {
	/**
	 * 
	 */
	private Context contenedorContext = null;
	/**
	 * 
	 */
	protected Layer parent = null;

	public DispatcherServletLayer(String id, int order,
			Context contenedorContext) {
		// super(id, order);
		this.contenedorContext = contenedorContext;
		init();
	}

	public Object getComponent(String name) {
		return getDispatcherApplicationContext().getBean(name);
	}

	public Object getComponent(String name, Class<?> clazz) {
		return getDispatcherApplicationContext().getBean(name, clazz);
	}

	public Map<?, ?> getComponentsOfType(Class<?> clazz) {
		return getDispatcherApplicationContext().getBeansOfType(clazz);
	}
	/*
	public Object getContext() {
		return getDispatcherApplicationContext();
	}
	*/
	public Layer getParent() {
		return this.parent;
	}

	public void refresh() {
		ConfigurableApplicationContext dispatcherApplicationContext = getDispatcherApplicationContext();

		if (dispatcherApplicationContext != null) {
			dispatcherApplicationContext.refresh();
		}
	}

	public void refresh(String file) {
		ConfigurableApplicationContext dispatcherApplicationContext = getDispatcherApplicationContext();
		if (dispatcherApplicationContext != null) {
			((XmlWebApplicationContext) dispatcherApplicationContext)
					.setConfigLocation(file);
			this.refresh();
		}
	}

	public void setParent(Layer parent) {
		this.parent = parent;
	}

	public void dispose() {
		ConfigurableApplicationContext dispatcherApplicationContext = getDispatcherApplicationContext();

		if (dispatcherApplicationContext != null) {
			dispatcherApplicationContext.close();
		}
	}

	private ConfigurableApplicationContext getDispatcherApplicationContext() {
		ConfigurableApplicationContext dispatcherApplicationContext = (ConfigurableApplicationContext) ContainerUtils
				.getServletDispatcherContext((ServletContext) this.contenedorContext
						.getExternal());
		return dispatcherApplicationContext;
	}

	@Override
	public void init() throws ComponentException {
		// TODO Auto-generated method stub
		
	}

}
