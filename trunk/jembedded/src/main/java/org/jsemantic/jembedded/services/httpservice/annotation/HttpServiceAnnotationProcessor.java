/*
 * Copyright 2007-2008, www.jsemantic.org, www.adolfoestevez.com,
 * http://semanticj2ee.blogspot.com/ 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jsemantic.jembedded.services.httpservice.annotation;

import java.lang.annotation.Annotation;

import org.jsemantic.jembedded.core.container.Container;
import org.jsemantic.jembedded.services.httpservice.HttpService;
import org.jsemantic.jembedded.services.httpservice.factory.HttpServiceFactory;

public class HttpServiceAnnotationProcessor {

	/** The Constant log. */
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger
			.getLogger(HttpServiceAnnotationProcessor.class);

	@SuppressWarnings("unchecked")
	public static HttpService processAnnotation(Class service) {

		HttpService httpService = (HttpService) Container
				.getService("httpService");
		if (service.isAnnotationPresent(HttpServiceConfiguration.class)) {
			Annotation ann = service
					.getAnnotation(HttpServiceConfiguration.class);
			return HttpServiceFactory.getInstance(httpService, ann);
		}
		return httpService;
	}

}
