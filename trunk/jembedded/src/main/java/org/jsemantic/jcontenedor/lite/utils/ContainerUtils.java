package org.jsemantic.jcontenedor.lite.utils;

import javax.servlet.ServletContext;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public final class ContainerUtils {
	
	private ContainerUtils() {
		
	}
	
	public static ConfigurableApplicationContext  getApplicationContext(ServletContext servletContext) {
		return (ConfigurableApplicationContext)WebApplicationContextUtils.getWebApplicationContext(servletContext);
	}
	
	public static ConfigurableApplicationContext getServletDispatcherContext(ServletContext servletContext) {
		return (ConfigurableApplicationContext)servletContext.getAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
	}
}
