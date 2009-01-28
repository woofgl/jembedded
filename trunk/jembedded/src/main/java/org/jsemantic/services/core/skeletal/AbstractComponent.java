package org.jsemantic.services.core.skeletal;

import org.jsemantic.services.core.Component;
import org.jsemantic.services.core.context.Context;
import org.jsemantic.services.core.exception.ComponentException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 
 * @author adolfo
 * 
 */
public abstract class AbstractComponent implements Component, InitializingBean,
		DisposableBean {

	public AbstractComponent() {
	}
	
	private Context context = null;
	
	
	public void setContext(Context context) {
		this.context = context;
	}

	protected STATE state = STATE.NOT_INITIALIZED;

	private String id = null;

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}

	public void afterPropertiesSet() throws Exception {
		this.state = STATE.INITIALIZED;
		init();
	}

	public void destroy() throws java.lang.Exception {
		this.state = STATE.DISPOSED;
		dispose();
	}
	
	public STATE getState() {
		return this.state;
	}
	
	public boolean isInitialized() {
		return this.state == STATE.INITIALIZED;
	}
	
	public Context getContext() {
		return this.context;
	}
	
	public abstract void init() throws ComponentException;

	public abstract void dispose() throws ComponentException;
}
