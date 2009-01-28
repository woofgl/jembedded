package org.jsemantic.services.core.service.skeletal;

import org.jsemantic.services.core.service.Service;
import org.jsemantic.services.core.service.exception.ServiceException;
import org.jsemantic.services.core.skeletal.AbstractComponent;
import org.springframework.beans.factory.BeanNameAware;


public abstract class AbstractService extends AbstractComponent implements Service, BeanNameAware {


	public void setBeanName(java.lang.String beanName) {
		super.setId(beanName);
	}
	
	public boolean isStarted() {
		return state == STATE.STARTED;
	}
	
	public void start() {
		this.state = STATE.STARTED;
		run();
	}

	public void stop() {
		this.state = STATE.STOPPED;
		pause();
	}
	
	public abstract void run() throws ServiceException;

	public abstract void pause() throws ServiceException;
}
	
