package org.jsemantic.jcontenedor.lite.context.impl;

import javax.servlet.ServletContext;

import org.jsemantic.services.core.context.Context;
import org.jsemantic.services.core.exception.ComponentException;
import org.jsemantic.services.core.skeletal.AbstractComponent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

public class ContextImpl extends AbstractComponent implements
		ServletContextAware, ApplicationContextAware, Context {

	private ServletContext servletContext = null;
	/**
	 * 
	 */
	private ApplicationContext applicationContext = null;

	public boolean isWebContext() {
		return servletContext != null;
	}

	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;

	}

	@Override
	public void dispose() throws ComponentException {
		applicationContext = null;
	}

	@Override
	public void init() throws ComponentException {

	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.applicationContext = arg0;
	}

	public Object getExternal() {
		if (isWebContext()) {
			return this.servletContext;
		}
		return this.applicationContext;
	}

}
