package org.jsemantic.jembedded.services.httpservice;

import javax.servlet.ServletContext;

import org.jsemantic.services.core.service.Service;
import org.mortbay.jetty.webapp.WebAppContext;

public interface HttpService extends Service {
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
