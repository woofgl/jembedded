package org.jsemantic.services.core.service;


/**
 * 
 * @author Adolfo Estevez</a>
 * @version 1.0
 * @since 1.0
 */
public interface Service extends ServiceCycle {

	/**
	 * 
	 * @return
	 */
	public Object getContext();
	/**
	 * 
	 * @return
	 */
	public String getId();

}