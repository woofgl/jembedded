package org.jsemantic.jcontenedor.lite.configuration.web;

import java.util.Map;

import javax.servlet.ServletContext;

import org.jsemantic.jcontenedor.lite.ContenedorLite;
import org.jsemantic.jcontenedor.lite.configuration.ContenedorLiteFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.CollectionFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * 
 * @author Adolfo Estevez
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class ContenedorLoader extends org.springframework.web.context.ContextLoader {
	
	/** Referencia al contenedor a crear */
	private ContenedorLite contenedor = null;
	
	/** Nombre de la propiedad bajo la cual se almacenará el Contenedor en Servlet Context. */
	public static String ROOT_CONTAINER_CONTEXT_ATTRIBUTE = ContenedorLite.class.getName() + ".ROOT";
	
	/** Map que almacena el contenedor en local Thread según el ClassLoader utilizado. */
	private static Map<ClassLoader, ContenedorLite> cCurrentContainerPerThread = (Map<ClassLoader, ContenedorLite>) CollectionFactory
			.createConcurrentMapIfPossible(1);

	/**
	 * Libera los recursos del contenedor web.
	 * @param pServletContext ServletContext
	 * Para mayor información:
	 * @see org.springframework.web.context.ContextLoader#closeWebApplicationContext(javax.servlet.ServletContext)
	 */
	public void closeWebApplicationContext(ServletContext pServletContext) {
		super.closeWebApplicationContext(pServletContext);
		try {
			this.contenedor.stop();
			this.contenedor = null;
		} finally {
			cCurrentContainerPerThread.remove(Thread.currentThread().getContextClassLoader());
			pServletContext.removeAttribute(ROOT_CONTAINER_CONTEXT_ATTRIBUTE);
		}
	}

	/**
	 * Devuelve el contenedor actúal.
	 * @return Container
	 */
	public static ContenedorLite getCurrentContainer() {
		return cCurrentContainerPerThread.get(Thread.currentThread().getContextClassLoader());
	}

	protected WebApplicationContext createWebApplicationContext(ServletContext servletContext, ApplicationContext parent)
			throws BeansException {

		this.contenedor =  ContenedorLiteFactory.getDefaultInstance(servletContext);
		this.contenedor.start();
		servletContext.setAttribute(ROOT_CONTAINER_CONTEXT_ATTRIBUTE, this.contenedor);
		cCurrentContainerPerThread.put(Thread.currentThread().getContextClassLoader(), this.contenedor);
		//XmlWebApplicationContext wac = (XmlWebApplicationContext)this.contenedor.getRootLayer().getParent().getContext();
		XmlWebApplicationContext wac = null;
		
		if (parent != null) {
			wac.setParent(parent);
		}
		customizeContext(servletContext, wac);
		return wac;
	}
	
}