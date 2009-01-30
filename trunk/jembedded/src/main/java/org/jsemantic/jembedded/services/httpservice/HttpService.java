package org.jsemantic.jembedded.services.httpservice;

import javax.servlet.ServletContext;

import org.jsemantic.services.core.service.Service;
import org.mortbay.jetty.webapp.WebAppContext;

public interface HttpService extends Service {
	/**
	 * 
	 * @param host
	 */
	public void setHost(String host);
	/**
	 * 
	 * @param rootContext
	 */
	public void setRootContext(String rootContext);
	/**
	 * 
	 * @param webApplication
	 */
	public void setWebApplication(String webApplication);
	/**
	 * 
	 * @param port
	 */
	public void setPort(int port);
	
	/**
	 * 
	 * @return
	 */
	public ServletContext getServerContext();
	/**
	 * 
	 * @return
	 */
	public WebAppContext getApplicationContext();
		
	
}
