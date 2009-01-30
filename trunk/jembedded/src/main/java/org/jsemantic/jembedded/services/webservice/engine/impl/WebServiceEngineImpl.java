package org.jsemantic.jembedded.services.webservice.engine.impl;

import javax.servlet.ServletContext;

import org.jsemantic.jembedded.services.httpservice.HttpService;
import org.jsemantic.jembedded.services.webservice.engine.WebServiceEngine;
import org.jsemantic.services.core.exception.ComponentException;
import org.jsemantic.services.core.service.exception.ServiceException;
import org.jsemantic.services.core.service.skeletal.AbstractService;
import org.mortbay.jetty.webapp.WebAppContext;

public class WebServiceEngineImpl extends AbstractService implements
		WebServiceEngine {

	private HttpService webHttpService = null;

	public void setWebHttpService(HttpService webHttpService) {
		this.webHttpService = webHttpService;
	}

	@Override
	public void pause() throws ServiceException {
		this.webHttpService.stop();
	}

	@Override
	public void run() throws ServiceException {
		this.webHttpService.start();
	}

	@Override
	public void dispose() throws ComponentException {
		webHttpService.dispose();
	}

	@Override
	public void init() throws ComponentException {
	}

	public WebAppContext getApplicationContext() {
		return webHttpService.getApplicationContext();
	}

	public ServletContext getServerContext() {
		return webHttpService.getServerContext();
	}

	public void setHost(String host) {
		webHttpService.setHost(host);
	}

	public void setRootContext(String rootContext) {
		webHttpService.setRootContext(rootContext);
	}

	public void setWebApplication(String webApplication) {
		webHttpService.setWebApplication(webApplication);
	}

	public void setPort(int port) {
		webHttpService.setPort(port);
	}

}
