package org.jsemantic.jembedded.services.webservice.engine.factory;

import java.lang.annotation.Annotation;

import org.jsemantic.jembedded.services.webservice.engine.WebServiceEngine;
import org.jsemantic.jembedded.services.webservice.engine.annotation.WebServiceEngineConfiguration;
import org.jsemantic.services.core.service.Service;
import org.springframework.util.StringUtils;

public class WebServiceEngineFactory {

	private WebServiceEngineFactory() {

	}

	public static Service getInstance(Service webServiceEngine, Annotation ann) {

		if (ann instanceof WebServiceEngineConfiguration) {
			String webApplication = ((WebServiceEngineConfiguration) ann).webApplication();
			int port = ((WebServiceEngineConfiguration) ann).port();
			String host = ((WebServiceEngineConfiguration) ann).host();
			String root = ((WebServiceEngineConfiguration) ann).root();
			
			if (StringUtils.hasText(host)) {
				((WebServiceEngine) webServiceEngine).setHost(host);
			}

			if (StringUtils.hasText(root)) {
				((WebServiceEngine) webServiceEngine).setRootContext(root);
			}

			if (StringUtils.hasText(webApplication)) {
				((WebServiceEngine) webServiceEngine)
						.setWebApplication(webApplication);
			}
			if (port > 0) {
				((WebServiceEngine) webServiceEngine).setPort(port);
			}
		}
		return webServiceEngine;
	}

}
