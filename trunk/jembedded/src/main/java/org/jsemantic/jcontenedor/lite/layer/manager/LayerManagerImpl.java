/**
 * 
 */
package org.jsemantic.jcontenedor.lite.layer.manager;

import java.util.List;

import org.jsemantic.jcontenedor.lite.layer.Layer;
import org.jsemantic.jcontenedor.lite.layer.strategy.Strategy;
import org.jsemantic.jcontenedor.lite.layer.strategy.impl.ClassPathLayerStrategy;
import org.jsemantic.jcontenedor.lite.layer.strategy.impl.WebLayerStrategy;
import org.jsemantic.jcontenedor.lite.registry.ServiceRegistry;
import org.jsemantic.jcontenedor.lite.registry.ServiceRegistryImpl;
import org.jsemantic.services.core.skeletal.AbstractComponent;

/**
 * 
 * @author aestevez
 * @see <<Insertar clases relacionadas>>
 */
public class LayerManagerImpl extends AbstractComponent implements LayerManager {
	/**
	 * 
	 */
	public static String SERVICE_LAYER_FILE = "classpath:META-INF/default/contenedor/core/layers/common-services.xml";
	/**
	 * Strategy resolver for web/classpath layer
	 */
	private Strategy layerResolver = null;
	/**
	 * Array containing the application layers
	 */
	private List<Layer> layers = null;
	/**
	 * Layer configuration files
	 */
	private List<String> layerFiles = null;
	/**
	 * 
	 */
	private ServiceRegistry registry = null;

	public void setLayerFiles(List<String> layerFiles) {
		this.layerFiles = layerFiles;
	}

	public Strategy getLayerResolver() {
		return layerResolver;
	}

	public void setLayerResolver(Strategy layerResolver) {
		this.layerResolver = layerResolver;
	}

	public void dispose() {
		if (this.layers != null)
			this.layers.clear();

		this.layers = null;
		this.layerResolver = null;
	}

	@Override
	public void init() {
		if (this.layerResolver == null) {
			if (super.getContext()!= null) {
				if (super.getContext().isWebContext()) {
					this.layerResolver = new WebLayerStrategy();
					return;
				}
			}
			this.layerResolver = new ClassPathLayerStrategy();
		}
	}

	public void resolve() throws LayerManagerException {
		this.layers = layerResolver.resolve(this.layerFiles, super
				.getContext());
		this.refreshLayers();

		this.registry = new ServiceRegistryImpl(this.getLayer(0));
	}

	public Layer getLayer(int order) throws LayerManagerException {
		return this.layers.get(order);
	}

	public void refreshLayer(int id) throws LayerManagerException {
		Layer layer = getLayer(id);
		if (layer != null) {
			layer.refresh();
		}
	}

	public void disposeLayer(int id) throws LayerManagerException {
		Layer layer = getLayer(id);
		if (layer != null) {
			layer.dispose();
		}
	}

	public void refreshLayers() {
		for (Layer layer : layers) {
			layer.refresh();
		}
	}

	public ServiceRegistry getRegistry() throws LayerManagerException {
		return this.registry;
	}

	public Layer getRootLayer() throws LayerManagerException {
		return getLayer(this.layers.size() - 1);
	}

	public void refreshRootLayer() throws LayerManagerException {
		Layer root = this.getRootLayer();
		root.refresh();
	}

	public void refreshRegistryLayer() throws LayerManagerException {
		Layer registry = this.getLayer(0);
		registry.refresh();
	}


}
