package org.jsemantic.jcontenedor.lite.layer.strategy;

import java.util.List;

import org.jsemantic.jcontenedor.lite.layer.Layer;
import org.jsemantic.services.core.context.Context;

/**
 * 
 * @author adolfo
 * 
 */
public interface Strategy {
	/**
	 * 
	 * @param files
	 * @param applicationContext
	 * @return
	 * @throws StrategyException
	 */
	public List<Layer> resolve(List<String> files, Context context)
			throws StrategyException;

}
