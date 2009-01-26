package org.jsemantic.services.core;

public interface Cycle {

	public enum STATE {
		NOT_INITIALIZED, INITIALIZED, STARTED, STOPPED, DISPOSED, DESTROYED,
	};

	public void start();

	public void stop();

	public boolean isStarted();

	public STATE getState();

}
