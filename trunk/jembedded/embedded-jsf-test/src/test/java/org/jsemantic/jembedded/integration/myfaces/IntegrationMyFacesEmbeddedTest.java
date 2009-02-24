package org.jsemantic.jembedded.integration.myfaces;

import org.jsemantic.jembedded.integration.myfaces.skeletal.AbstractMyFacesIntegrationTest;
import org.jsemantic.services.httpservice.annotation.HttpService;
import org.jsemantic.services.httpservice.annotation.HttpServiceConfiguration;

@HttpService
@HttpServiceConfiguration(host="", port=9006, root="/test", webApplication="src/main/resources/webapp/myfaces")
public class IntegrationMyFacesEmbeddedTest extends AbstractMyFacesIntegrationTest {

	@Override
	public void test() throws Exception {
		super.assertRuntimeConfigNotNull();
		super.assertManagedBeanNotNull("testBean");
	}
}
