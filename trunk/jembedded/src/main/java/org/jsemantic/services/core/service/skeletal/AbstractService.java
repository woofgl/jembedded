package org.jsemantic.services.core.service.skeletal;

import org.jsemantic.services.core.service.Service;
import org.jsemantic.services.core.skeletal.AbstractComponent;
import org.springframework.beans.factory.BeanNameAware;


public abstract class AbstractService extends AbstractComponent implements Service, BeanNameAware {

	private Object containerContext = null;
	
	public Object getContext() {
		return this.containerContext;
	}

	public void setBeanName(java.lang.String beanName) {
		super.setId(beanName);
	}
	
}
