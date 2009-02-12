package org.jsemantic.jembedded.core.annotation;

import org.jsemantic.jservice.core.service.Service;

public interface AnnotationProcessor {
	/**
	 * 
	 * @param service
	 * @param serviceClazz
	 * @return
	 */
	public boolean processAnnotation(Service service, Class<?> serviceClazz);
	
}
