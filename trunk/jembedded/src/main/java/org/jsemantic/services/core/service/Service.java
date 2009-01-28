package org.jsemantic.services.core.service;

import org.jsemantic.services.core.context.Context;


/**
 * 
 * @author Adolfo Estevez</a>
 * @version 1.0
 * @since 1.0
 */
public interface Service extends ServiceCycle {
	
	/**
	 * 
	 * @param applicationContext
	 */
	public void setContext(Context context);
	
	/**
	 * 
	 * @return
	 */
	public Context getContext();
	/**
	 * 
	 * @return
	 */
	public String getId();

}