package org.jsemantic.jcontenedor.lite.layer.strategy.impl;

import org.jsemantic.jcontenedor.lite.layer.Layer;
import org.jsemantic.jcontenedor.lite.layer.WebLayer;
import org.jsemantic.jcontenedor.lite.layer.strategy.Strategy;
import org.jsemantic.services.core.context.Context;

public class WebLayerStrategy extends AbstractStrategy implements Strategy {

	/**
	 * 
	 */
	protected Layer getLayerInstance(Context context, String file,
			String id, int order) {
		return new WebLayer(id, file, context, order);
	}

}
