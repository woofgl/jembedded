package org.jsemantic.jcontenedor.lite.configuration.web;

import javax.servlet.ServletContextListener;

/**
 * 
 * @author adolfo
 *
 */
public class ContenedorListener extends
		org.springframework.web.context.ContextLoaderListener implements ServletContextListener {
	/**
	 * 
	 */
	protected org.springframework.web.context.ContextLoader createContextLoader() {
		return new ContenedorLoader();
	}	
}