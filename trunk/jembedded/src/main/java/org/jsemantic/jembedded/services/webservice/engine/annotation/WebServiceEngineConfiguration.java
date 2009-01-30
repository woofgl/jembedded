/**
 * 
 */
package org.jsemantic.jembedded.services.webservice.engine.annotation;

/**
 * @author Owner
 *
 */
public @interface WebServiceEngineConfiguration {
	/**
	 * 
	 * @return
	 */
	public int port();
	/**
	 * 
	 * @return
	 */
	public String host();
	/**
	 * 
	 * @return
	 */
	public String root();
	
	/**
	 * 
	 * @return
	 */
	public String webApplication();
	
}
