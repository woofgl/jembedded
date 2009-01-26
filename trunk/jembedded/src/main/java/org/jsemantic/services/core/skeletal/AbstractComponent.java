package org.jsemantic.services.core.skeletal;

import org.jsemantic.services.core.Component;
import org.jsemantic.services.core.Cycle;
import org.jsemantic.services.core.Cycle.STATE;
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

	public AbstractComponent(String id) {
		this.id = id;
	}

	private STATE state = STATE.NOT_INITIALIZED;

	private String id = null;

	public void setId(String id) {
		this.id = id;
	}
	
	public void start() {
		this.state = STATE.STARTED;
		run();
	}

	public void stop() {
		this.state = STATE.STOPPED;
		pause();
	}
	
	public String getId() {
		return this.id;
	}

	public boolean isStarted() {
		return this.state == STATE.STARTED;
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
	
	public abstract void run() throws ComponentException;
	
	public abstract void pause() throws ComponentException;
	
	public abstract void init() throws ComponentException;

	public abstract void dispose() throws ComponentException;
}
