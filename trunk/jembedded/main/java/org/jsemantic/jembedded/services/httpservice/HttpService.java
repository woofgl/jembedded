package org.jsemantic.jembedded.services.httpservice;

import javax.servlet.ServletContext;

import org.mortbay.jetty.webapp.WebAppContext;

public interface HttpService {
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
		
	/**
	 * 
	 * @throws Exception
	 */
	public void start() throws Exception;
	/**
	 * 
	 * @throws Exception
	 */
	public void stop() throws Exception;
	/**
	 * 
	 * @throws Exception
	 */
	public void dispose() throws Exception;
	
}
