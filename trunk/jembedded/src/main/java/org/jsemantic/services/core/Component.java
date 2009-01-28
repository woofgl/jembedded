package org.jsemantic.services.core;

import org.jsemantic.services.core.context.Context;


/**
 * 
 * @author adolfo
 *
 */ 
public interface Component extends Cycle {
	/**
	 * Component Id.
	 * 
	 * @return
	 */
	public String getId();
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
}
