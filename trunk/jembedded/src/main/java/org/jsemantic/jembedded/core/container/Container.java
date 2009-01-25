package org.jsemantic.jembedded.core.container;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

public class Container {

	public final static String DEFAULT_CONFIGURATION_FILE = "classpath:META-INF/default/configuration/jembedded-configuration.xml";

	private static Container container = null;
	
	private static ApplicationContext context = null;

	/**
	 * 
	 */
	private Container() {
		context = getDefaultInstance();
	}
	
	public static Container getInstance() {
		if (container == null) {
			container = new Container();
		}
		return container;
	}
	
	public static Object getService(String id) {
		return context.getBean(id);
	}
	
	private static ApplicationContext getInstance(String configurationFile) {
		if (!StringUtils.hasText(configurationFile)) {
			return getDefaultInstance();
		}
		return getContenedor(configurationFile);
	}

	/**
	 * 
	 * @return
	 */
	private static ApplicationContext getDefaultInstance() {
		return getContenedor(DEFAULT_CONFIGURATION_FILE);
	}

	private static ApplicationContext getContenedor(String configurationFile) {
		ClassPathXmlApplicationContext contenedorCtx = new ClassPathXmlApplicationContext();
		contenedorCtx.setConfigLocation(configurationFile);
		contenedorCtx.refresh();
		contenedorCtx.registerShutdownHook();
		return contenedorCtx;
	}

}
