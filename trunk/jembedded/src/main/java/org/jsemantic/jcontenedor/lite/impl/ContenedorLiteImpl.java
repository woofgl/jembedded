package org.jsemantic.jcontenedor.lite.impl;

import org.jsemantic.jcontenedor.lite.ContenedorLite;
import org.jsemantic.jcontenedor.lite.layer.manager.LayerManager;
import org.jsemantic.services.core.Component;
import org.jsemantic.services.core.context.Context;
import org.jsemantic.services.core.exception.ComponentException;
import org.jsemantic.services.core.service.Service;
import org.jsemantic.services.core.service.exception.ServiceException;
import org.jsemantic.services.core.service.skeletal.AbstractService;

public class ContenedorLiteImpl extends AbstractService implements
		ContenedorLite {

	private LayerManager layerManager = null;

	public ContenedorLiteImpl() {

	}

	public Object getBean(String id) {
		return layerManager.getRootLayer().getComponent(id);
	}

	public Component getComponent(String id) throws ComponentException {
		return (Component) layerManager.getRootLayer().getComponent(id);
	}

	public Context getContext() {
		return super.getContext();
	}

	public Service getService(String id) throws ServiceException {
		return layerManager.getRegistry().getService(id);
	}

	@Override
	public void pause() throws ServiceException {
		dispose();
	}

	@Override
	public void run() throws ServiceException {
		this.layerManager.resolve();
	}

	@Override
	public void dispose() throws ComponentException {
		this.layerManager.dispose();
	}

	@Override
	public void init() throws ComponentException {
		// TODO Auto-generated method stub
	}

	public void setLayerManager(LayerManager layerManager) {
		this.layerManager = layerManager;
	}

}
