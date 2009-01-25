package org.jsemantic.jembedded.services.httpservice.factory;

import java.lang.annotation.Annotation;

import org.jsemantic.jembedded.services.httpservice.HttpService;
import org.jsemantic.jembedded.services.httpservice.annotation.HttpServiceConfiguration;
import org.jsemantic.jembedded.services.httpservice.impl.HttpServiceImpl;
import org.springframework.util.StringUtils;

public class HttpServiceFactory {
	
	private HttpServiceFactory() {
		
	}
	
	public static HttpService getInstance (HttpService httpService, Annotation ann) {
		
		if (ann instanceof HttpServiceConfiguration) {
			String host = ((HttpServiceConfiguration) ann).host();
			String root = ((HttpServiceConfiguration) ann).root();
			String webApplication = ((HttpServiceConfiguration) ann).webApplication();
			int port = ((HttpServiceConfiguration) ann).port();
	

			if (StringUtils.hasText(host)) {
				((HttpServiceImpl)httpService).setHost(host);
			}

			if (StringUtils.hasText(root)) {
				((HttpServiceImpl)httpService).setRootContext(root);
			}

			if (StringUtils.hasText(webApplication)) {
				((HttpServiceImpl)httpService).setWebApplication(webApplication);
			}
			if (port > 0) {
				((HttpServiceImpl)httpService).setPort(port);
			}
			
		}
		
		return httpService;
	}
	
}
