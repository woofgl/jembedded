package org.jsemantic.jcontenedor.lite.configuration;

import javax.servlet.ServletContext;

import org.jsemantic.jcontenedor.lite.ContenedorLite;
import org.springframework.beans.BeanUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class ContenedorLiteFactory {

	public final static String DEFAULT_CONFIGURATION_FILE = "classpath:META-INF/default/configuration/contenedor-configuration.xml";

	public final static String WEB_DEFAULT_CONFIGURATION_FILE = "classpath:META-INF/default/configuration/contenedor-configuration.xml";

	/**
	 * 
	 */
	private ContenedorLiteFactory() {
	}

	public static ContenedorLite getInstance(String configurationFile) {
		if (!StringUtils.hasText(configurationFile)) {
			return getDefaultInstance();
		}
		return getContenedor(configurationFile);
	}

	/**
	 * 
	 * @return
	 */
	public static ContenedorLite getDefaultInstance() {
		return getContenedor(DEFAULT_CONFIGURATION_FILE);
	}

	private static ContenedorLite getContenedor(String configurationFile) {
		ClassPathXmlApplicationContext contenedorCtx = new ClassPathXmlApplicationContext();
		contenedorCtx.setConfigLocation(configurationFile);
		contenedorCtx.refresh();
		contenedorCtx.registerShutdownHook();
		
		ContenedorLite contenedor = (ContenedorLite)contenedorCtx.getBean("container");
		
		return contenedor;
	}

	public static ContenedorLite getDefaultInstance(
			ServletContext servletContext) {
		return getContenedorWeb(WEB_DEFAULT_CONFIGURATION_FILE, servletContext);
	}

	private static ContenedorLite getContenedorWeb(String configurationFile,
			ServletContext servletContext) {
		ConfigurableWebApplicationContext wac = (ConfigurableWebApplicationContext) BeanUtils
				.instantiateClass(XmlWebApplicationContext.class);
		wac.setConfigLocation(configurationFile);
		wac.setServletContext(servletContext);
		wac.refresh();
		ContenedorLite contenedor = (ContenedorLite)wac.getBean("container");
		return contenedor;
	}

}
