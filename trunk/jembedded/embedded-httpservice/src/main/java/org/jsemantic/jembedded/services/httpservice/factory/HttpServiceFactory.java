package org.jsemantic.jembedded.services.httpservice.factory;

import java.lang.annotation.Annotation;

import org.jsemantic.jembedded.services.httpservice.HttpService;
import org.jsemantic.jembedded.services.httpservice.annotation.HttpServiceConfiguration;
import org.jsemantic.jservice.core.service.Service;
import org.springframework.util.StringUtils;

public class HttpServiceFactory {

	public HttpServiceFactory() {

	}

	public static Service getInstance(Service httpService, Annotation ann) {

		if (ann instanceof HttpServiceConfiguration) {
			String host = ((HttpServiceConfiguration) ann).host();
			String root = ((HttpServiceConfiguration) ann).root();
			String webApplication = ((HttpServiceConfiguration) ann)
					.webApplication();
			int port = ((HttpServiceConfiguration) ann).port();

			if (StringUtils.hasText(host)) {
				((HttpService) httpService).setHost(host);
			}

			if (StringUtils.hasText(root)) {
				((HttpService) httpService).setRootContext(root);
			}

			if (StringUtils.hasText(webApplication)) {
				((HttpService) httpService)
						.setWebApplication(webApplication);
			}
			if (port > 0) {
				((HttpService) httpService).setPort(port);
			}

		}

		return httpService;
	}

}
