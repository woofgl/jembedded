package org.jsemantic.jembedded.common.webservice;

import javax.jws.WebService;

@WebService(endpointInterface = "org.jsemantic.jembedded.common.webservice.TestWebService")
public class TestWebServiceImpl implements TestWebService {

	public boolean test() {
		return true;
	}

}
