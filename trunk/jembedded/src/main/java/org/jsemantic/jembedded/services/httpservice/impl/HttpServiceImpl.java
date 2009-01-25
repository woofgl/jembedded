package org.jsemantic.jembedded.services.httpservice.impl;

import javax.servlet.ServletContext;

import org.jsemantic.jembedded.services.httpservice.HttpService;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class HttpServiceImpl implements HttpService {

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
	/*
	public HttpServiceImpl(String rootContext, String webApp) {
		this.rootContext = rootContext;
		this.webApplication =  webApp;
	}
	
	public HttpServiceImpl(String rootContext, String webApp, String host, int port) {
		this(rootContext, webApp);
		this.host = host;
		this.port = port;
	}
	*/
	public void setHost(String host) {
		this.host = host;
	}

	public void setRootContext(String rootContext) {
		this.rootContext = rootContext;
	}

	public void setWebApplication(String webApplication) {
		this.webApplication = webApplication;
	}
	
	private void init() {
		embeddedServer = new org.mortbay.jetty.Server();

		if (connectors != null) {
			embeddedServer.setConnectors(connectors);
		} else {
			embeddedServer.setConnectors(getDefaultConnectors());
		}
		this.webApplicationContext = createWebApplicationContext();
		this.handlers = createDefaultHandlers(webApplicationContext);
		this.embeddedServer.setHandler(this.handlers);
	}

	public void start() throws Exception {
		init();
		this.embeddedServer.start();
	}

	public void stop() throws Exception {
		this.embeddedServer.stop();
	}

	public void dispose() {
		this.embeddedServer.destroy();
		this.embeddedServer = null;
		this.connectors = null;
		this.handlers = null;
		this.webApplicationContext = null;
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
