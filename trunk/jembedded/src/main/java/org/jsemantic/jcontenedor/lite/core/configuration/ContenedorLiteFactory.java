package org.jsemantic.jcontenedor.lite.core.configuration;

import org.jsemantic.jcontenedor.lite.core.ContenedorLite;
import org.jsemantic.jcontenedor.lite.core.impl.ContenedorLiteImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

public class ContenedorLiteFactory {

	public final static String DEFAULT_CONFIGURATION_FILE = "classpath:META-INF/default/configuration/jembedded-configuration.xml";

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
		return ContenedorLiteImpl.getInstance(contenedorCtx);
	}

}
