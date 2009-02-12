package org.jsemantic.jembedded.services.webservice.engine.impl;

import javax.servlet.ServletContext;

import org.jsemantic.jembedded.services.httpservice.HttpService;
import org.jsemantic.jembedded.services.webservice.engine.WebServiceEngine;
import org.jsemantic.jservice.core.service.exception.ServiceException;
import org.jsemantic.jservice.core.service.skeletal.AbstractManagedService;
import org.mortbay.jetty.webapp.WebAppContext;

public class WebServiceEngineImpl extends AbstractManagedService implements
		WebServiceEngine {

	private HttpService webHttpService = null;

	public void setWebHttpService(HttpService webHttpService) {
		this.webHttpService = webHttpService;
	}

	@Override
	protected void stopService() throws ServiceException {
		this.webHttpService.stop();
		this.webHttpService.dispose();
	}

	@Override
	protected void startService() throws ServiceException {
		this.webHttpService.start();
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
