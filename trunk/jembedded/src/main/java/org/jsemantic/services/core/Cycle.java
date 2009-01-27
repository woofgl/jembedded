package org.jsemantic.services.core;

import org.jsemantic.services.core.exception.ComponentException;

public interface Cycle {

	public enum STATE {
		NOT_INITIALIZED, INITIALIZED,  STARTED, STOPPED, DISPOSED
	};

	public void init() throws ComponentException;
	
	public void dispose() throws ComponentException;

	public boolean isInitialized();
	
	public STATE getState();

}
