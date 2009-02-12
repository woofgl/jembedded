package org.jsemantic.jembedded.services.httpservice.impl;

import javax.servlet.ServletContext;

import org.jsemantic.jembedded.services.httpservice.HttpService;
import org.jsemantic.jservice.core.service.exception.ServiceException;
import org.jsemantic.jservice.core.service.skeletal.AbstractManagedService;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class HttpServiceImpl extends AbstractManagedService implements HttpService {

	private org.mortbay.jetty.Server embeddedServer = null;

	private Connector[] connectors = null;

	protected WebAppContext webApplicationContext = null;

	private HandlerCollection handlers = null;

	private int port = 9005;

	private String host = "127.0.0.1";

	private String rootContext = "/";
	
	private String webApplication = "src/main/resources/webapp/test";
	
	public HttpServiceImpl() {
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setRootContext(String rootContext) {
		this.rootContext = rootContext;
	}

	public void setWebApplication(String webApplication) {
		this.webApplication = webApplication;
	}
	
	
	protected void startService()  {
		embeddedServer = new org.mortbay.jetty.Server();
		if (connectors != null) {
			embeddedServer.setConnectors(connectors);
		} else {
			embeddedServer.setConnectors(getDefaultConnectors());
		}
		this.webApplicationContext = createWebApplicationContext();
		this.handlers = createDefaultHandlers(webApplicationContext);
		this.embeddedServer.setHandler(this.handlers);
		try {
			this.embeddedServer.start();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	protected void stopService() {
		try {
			this.embeddedServer.stop();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		finally {
			this.embeddedServer.destroy();
			this.embeddedServer = null;
		}
	}
	
	public void setConnectors(Connector... connectors) {
		this.connectors = connectors;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public ServletContext getServerContext() {
		return webApplicationContext.getServletContext();
	}
	
	public WebAppContext getApplicationContext() {
		return webApplicationContext;
	}
	
	private HandlerCollection createDefaultHandlers(
			WebAppContext webApplicationContext) {
		HandlerCollection handlers = new HandlerCollection();
		handlers.setHandlers(new Handler[] { webApplicationContext,
				new DefaultHandler() });
		return handlers;
	}

	private WebAppContext createWebApplicationContext() {
		webApplicationContext = new WebAppContext();
		webApplicationContext.setContextPath(rootContext);
		webApplicationContext.setWar(webApplication);
		return webApplicationContext;
	}

	private Connector[] getDefaultConnectors() {
		SelectChannelConnector defaultConnector = new SelectChannelConnector();
		defaultConnector.setPort(this.port);
		defaultConnector.setHost(this.host);
		return new Connector[] { defaultConnector };
	}

}
