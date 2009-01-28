package org.jsemantic.jcontenedor.lite.registry;

import java.util.Map;

import org.jsemantic.services.core.context.Context;
import org.jsemantic.services.core.service.Service;


public interface ServiceRegistry {
	/**
	 * 
	 * @param serviceName
	 * @return
	 */
	public Service getService(String serviceName);
	/**
	 * 
	 * @return
	 */
	public Map<String, Service> getServices();
	/**
	 * 
	 * @param serviceName
	 * @param serviceClazz
	 * @return
	 */
	public Service getService (String serviceName, Class<?> serviceClazz);
	/**
	 * 
	 * @param containerContext
	 */
	public void setContainerContext(Context containerContext);
	
	
}
