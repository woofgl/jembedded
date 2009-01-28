package org.jsemantic.jcontenedor.lite.layer.manager;

import org.jsemantic.jcontenedor.lite.layer.Layer;
import org.jsemantic.jcontenedor.lite.layer.strategy.Strategy;
import org.jsemantic.jcontenedor.lite.registry.ServiceRegistry;
import org.jsemantic.services.core.Component;

public interface LayerManager extends Component {
	
	/**
	 * 
	 * @param applicationContext
	 * @throws LayerManagerException
	 */
	public void resolve() throws LayerManagerException;
	
	/**
	 * 
	 * @param id
	 * @throws LayerManagerException
	 */
	public void refreshLayer(int order) throws LayerManagerException;
	/**
	 * 
	 * @param id
	 * @throws LayerManagerException
	 */
	public void disposeLayer(int order) throws LayerManagerException;
	
	/**
	 * 
	 * @param order
	 * @return
	 * @throws LayerManagerException
	 */
	public Layer getLayer(int order) throws LayerManagerException;
	
	/**
	 * 
	 * @return
	 * @throws LayerManagerException
	 */
	public ServiceRegistry getRegistry()  throws LayerManagerException;
	
	/**
	 * 
	 * @return
	 * @throws LayerManagerException
	 */
	public Layer getRootLayer()  throws LayerManagerException;
	
	 /**
	  * 
	  * @throws LayerManagerException
	  */
	public void refreshRootLayer() throws LayerManagerException;
	/**
	 * 
	 * @throws LayerManagerException
	 */
	public void refreshRegistryLayer() throws LayerManagerException;
	
	/**
	 * 
	 */
	public void refreshLayers();

	/**
	 * 
	 * @param layerResolver
	 */
	public void setLayerResolver(Strategy layerResolver);
}
