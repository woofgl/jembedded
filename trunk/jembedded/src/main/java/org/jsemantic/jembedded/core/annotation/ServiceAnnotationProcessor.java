package org.jsemantic.jembedded.core.annotation;

import java.util.Map;
import java.util.WeakHashMap;

import org.jsemantic.jcontenedor.lite.ContenedorLite;
import org.jsemantic.jembedded.services.dbservice.annotation.DBServiceAnnotationProcessor;
import org.jsemantic.jembedded.services.httpservice.annotation.HttpServiceAnnotationProcessor;
import org.jsemantic.jembedded.services.webservice.engine.annotation.WebServiceEngineAnnotationProcessor;
import org.jsemantic.services.core.service.Service;

public class ServiceAnnotationProcessor {

	public static Map<String, Service> processAnnotation(
			ContenedorLite contenedor, Class<?> service) throws Exception {

		Map<String, Service> services = new WeakHashMap<String, Service>();

		Service httpService = HttpServiceAnnotationProcessor.processAnnotation(
				contenedor, service);
		if (httpService != null)
			services.put(httpService.getId(), httpService);

		Service dbService = DBServiceAnnotationProcessor.processAnnotation(
				contenedor, service);
		if (dbService != null)
			services.put(dbService.getId(), dbService);

		Service webServiceEngine = WebServiceEngineAnnotationProcessor
				.processAnnotation(contenedor, service);

		if (webServiceEngine != null)
			services.put(webServiceEngine.getId(), webServiceEngine);
		
		Service container = contenedor.getService("container");
		
		if (container != null)
			services.put(container.getId(), container);
		return services;
	}

}
