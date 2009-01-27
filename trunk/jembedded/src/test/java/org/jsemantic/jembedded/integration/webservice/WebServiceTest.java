package org.jsemantic.jembedded.integration.webservice;

import org.jsemantic.jembedded.integration.http.skeletal.AbstractHttpEmbeddTest;
import org.jsemantic.jembedded.services.httpservice.annotation.HttpServiceConfiguration;

@HttpServiceConfiguration(host = "", port = 9006, root = "/webservice", webApplication = "src/main/resources/webapp/webserviceengine")
public class WebServiceTest extends AbstractHttpEmbeddTest {

	@Override
	public void test() throws Exception {

		String wsdlURI = "http://localhost:9006/webservice/test?wsdl";

		String serviceURI = "http://localhost:9006/webservice/test/test";
		
		super.assertResponseStatusOK(wsdlURI);

		super.assertResponseNotNull(serviceURI);


	}

}
