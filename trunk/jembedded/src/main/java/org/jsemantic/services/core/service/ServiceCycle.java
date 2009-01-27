package org.jsemantic.services.core.service;

import org.jsemantic.services.core.Cycle;
import org.jsemantic.services.core.service.exception.ServiceException;

public interface ServiceCycle extends Cycle {
	
	public void start() throws ServiceException;

	public void stop() throws ServiceException;
	
	public boolean isStarted();
	
}
