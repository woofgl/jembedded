package org.jsemantic.jembedded.core.annotation;

import java.util.Map;
import java.util.WeakHashMap;

import org.jsemantic.jcontenedor.lite.ContenedorLite;
import org.jsemantic.jservice.core.service.Service;

public class ServiceAnnotationProcessor {

	private ServiceAnnotationProcessor() {

	}

	public static Map<String, Service> processAnnotation(
			ContenedorLite contenedor, Class<?> service) throws Exception {

		Map<String, Service> services = new WeakHashMap<String, Service>();
		Service httpService = null;
		try {
			httpService = contenedor.getService("httpService");
			if (httpService != null) {
				AnnotationProcessor processor = (AnnotationProcessor) contenedor
						.getBean("httpAnnotationProcessor");

				boolean processed = processor.processAnnotation(httpService,
						service);
				if (processed) {
					services.put("httpService", httpService);
				}
			}
		} catch (Throwable e) {

		}

		Service webHttpService = null;
		try {
			webHttpService = contenedor.getService("webHttpService");

			if (webHttpService != null) {
				AnnotationProcessor processor = (AnnotationProcessor) contenedor
						.getBean("webServiceAnnotationProcessor");

				boolean processed = processor.processAnnotation(webHttpService,
						service);
				if (processed) {
					services.put("webHttpService", webHttpService);
				}
			}
		} catch (Throwable e) {

		}

		Service dbService = null;
		try {
			dbService = contenedor.getService("dbService");
			if (dbService != null) {
				AnnotationProcessor processor = (AnnotationProcessor) contenedor
						.getBean("dbServiceAnnotationProcessor");

				boolean processed = processor.processAnnotation(dbService,
						service);
				if (processed) {
					services.put("dbService", dbService);
				}
			}
		} catch (Throwable e) {
		}

		Service container = null;
		try {
			contenedor.getService("container");
			if (container != null)
				services.put(container.getId(), container);
		} catch (Throwable e) {
		}
		return services;

	}

}
