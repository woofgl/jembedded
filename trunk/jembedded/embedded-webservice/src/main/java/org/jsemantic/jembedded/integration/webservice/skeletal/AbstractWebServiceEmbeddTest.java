package org.jsemantic.jembedded.integration.webservice.skeletal;

import org.jsemantic.jembedded.integration.http.skeletal.AbstractHttpEmbeddTest;
import org.jsemantic.jembedded.integration.webservice.WebServiceEmbeddTest;
import org.jsemantic.jembedded.services.httpservice.HttpService;

public abstract class AbstractWebServiceEmbeddTest extends
		AbstractHttpEmbeddTest implements WebServiceEmbeddTest {

	protected HttpService getHttpService() {
		return ((HttpService)getService("webServiceEngine"));
	}

	public abstract void test() throws Exception;
}
