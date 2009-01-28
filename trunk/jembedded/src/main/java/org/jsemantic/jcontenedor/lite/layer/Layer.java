package org.jsemantic.jcontenedor.lite.layer;

import java.util.Map;

import org.jsemantic.services.core.Component;

public interface Layer extends Component {
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Object getComponent (String name);
	/**
	 * 
	 * @param name
	 * @param clazz
	 * @return
	 */
	public Object getComponent(String name, Class<?> clazz);
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public Map<?,?> getComponentsOfType(Class<?> clazz);
	
	/**
	 * 
	 * @param parent
	 */
	public void setParent(Layer parent);
	/**
	 * 
	 * @return
	 */
	public Layer getParent();
	
	/**
	 * 
	 */
	public void refresh();
	/**
	 * Layer file
	 * @param file
	 */
	public void refresh(String file);
	
}
