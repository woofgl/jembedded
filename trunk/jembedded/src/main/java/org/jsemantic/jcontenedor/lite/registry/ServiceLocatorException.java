package org.jsemantic.jcontenedor.lite.registry;


public class ServiceLocatorException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ServiceLocatorException(String message) {
		super(message);
	}
	
	public ServiceLocatorException(String message, Throwable thr) {
		super(message, thr);
	}
}