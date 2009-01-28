package org.jsemantic.jcontenedor.lite;

import org.jsemantic.services.core.Component;
import org.jsemantic.services.core.context.Context;
import org.jsemantic.services.core.exception.ComponentException;
import org.jsemantic.services.core.service.Service;
import org.jsemantic.services.core.service.exception.ServiceException;

public interface ContenedorLite extends Service {
	/**
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public Service getService(String id) throws ServiceException;
	/**
	 * 
	 * @param id
	 * @return
	 * @throws ComponentException
	 */
	public Component getComponent(String id) throws ComponentException;
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Object getBean(String id);
	/**
	 * 
	 * @return
	 */
	public Context getContext();
	
}
