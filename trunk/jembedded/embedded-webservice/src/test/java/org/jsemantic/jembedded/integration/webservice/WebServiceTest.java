package org.jsemantic.jembedded.integration.webservice;

import org.jsemantic.jembedded.integration.webservice.skeletal.AbstractWebServiceEmbeddTest;
import org.jsemantic.jembedded.services.webservice.engine.annotation.WebServiceEngine;

@WebServiceEngine
public class WebServiceTest extends AbstractWebServiceEmbeddTest {

	@Override
	public void test() throws Exception {

		String wsdlURI = "http://localhost:9006/test/test?wsdl";

		String serviceURI = "http://localhost:9006/test/test/test";

		super.assertResponseStatusOK(wsdlURI);

		super.assertResponseNotNull(serviceURI);
	}
}
