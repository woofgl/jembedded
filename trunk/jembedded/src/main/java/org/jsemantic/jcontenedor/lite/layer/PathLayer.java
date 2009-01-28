package org.jsemantic.jcontenedor.lite.layer;

import java.util.Map;

import org.jsemantic.services.core.skeletal.AbstractComponent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PathLayer extends AbstractComponent implements Layer {
	
	/**
	 * 
	 */
	protected String file = null;

	/**
	 * 
	 */
	protected ConfigurableApplicationContext applicationContext = null;
	/**
	 * 
	 */
	protected Layer parent = null;
	
	public PathLayer (ConfigurableApplicationContext context) {
		this.applicationContext = context;
		init();
	}
	
	public PathLayer(String id, String file, int order) {
		//super(id, order);
		this.file = file;
		init();
	}

	public void init() {
		this.applicationContext = new ClassPathXmlApplicationContext();
		//((ClassPathXmlApplicationContext) this.context).setId(super.getId());
		((ClassPathXmlApplicationContext) this.applicationContext)
				.setConfigLocation(this.file);
		this.applicationContext.registerShutdownHook();
	}

	public Object getComponent(String name) {
		return this.applicationContext.getBean(name);
	}

	public Object getComponent(String name, Class<?> clazz) {
		return this.applicationContext.getBean(name, clazz);
	}

	public Map<?, ?> getComponentsOfType(Class<?> clazz) {
		return this.applicationContext.getBeansOfType(clazz);
	}

	public void refresh() {
		this.applicationContext.refresh();
	}

	public void setParent(Layer parent) {
		this.parent = parent;
		this.applicationContext.setParent((ConfigurableApplicationContext) parent
				.getContext());
	}

	public Layer getParent() {
		return this.parent;
	}

	public void dispose() {
		this.applicationContext.close();
	}
/*
	public  ConfigurableApplicationContext  getContext() {
		return this.applicationContext;
	}
*/
	public void refresh(String file) {
		this.file = file;
		((ClassPathXmlApplicationContext) this.applicationContext)
		.setConfigLocation(this.file);
		this.refresh();	
	}
}
