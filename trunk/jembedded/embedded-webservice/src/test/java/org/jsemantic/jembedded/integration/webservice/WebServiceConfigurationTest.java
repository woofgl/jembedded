package org.jsemantic.jembedded.integration.webservice;

import org.jsemantic.jembedded.integration.webservice.skeletal.AbstractWebServiceEmbeddTest;
import org.jsemantic.jembedded.services.webservice.engine.annotation.WebServiceEngine;
import org.jsemantic.jembedded.services.webservice.engine.annotation.WebServiceEngineConfiguration;

@WebServiceEngine
@WebServiceEngineConfiguration(host = "", port = 0, root = "", webApplication = "/src/main/resources/webapp/webservice/")
public class WebServiceConfigurationTest extends AbstractWebServiceEmbeddTest {

	@Override
	public void test() throws Exception {
		String wsdlURI = "http://localhost:9006/test/test?wsdl";
		String serviceURI = "http://localhost:9006/test/test/test";
		super.assertResponseStatusOK(wsdlURI);
		super.assertResponseNotNull(serviceURI);
	}

}
