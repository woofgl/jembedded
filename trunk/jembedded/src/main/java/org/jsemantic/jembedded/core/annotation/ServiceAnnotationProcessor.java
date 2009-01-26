package org.jsemantic.jembedded.core.annotation;

import java.util.Map;
import java.util.WeakHashMap;

import org.jsemantic.jembedded.services.dbservice.annotation.DBServiceAnnotationProcessor;
import org.jsemantic.jembedded.services.httpservice.annotation.HttpServiceAnnotationProcessor;
import org.jsemantic.services.core.service.Service;

public class ServiceAnnotationProcessor {

	public static Map<String, Service> processAnnotation(Class<?> service)
			throws Exception {

		Map<String, Service> services = new WeakHashMap<String, Service>();

		Service httpService = HttpServiceAnnotationProcessor
				.processAnnotation(service);
		if (httpService != null)
			services.put(httpService.getId(), httpService);

		Service dbService = DBServiceAnnotationProcessor
				.processAnnotation(service);
		if (dbService != null)
			services.put(dbService.getId(), dbService);

		return services;
	}

}
