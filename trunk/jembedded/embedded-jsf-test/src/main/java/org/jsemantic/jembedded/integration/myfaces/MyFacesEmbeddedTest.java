package org.jsemantic.jembedded.integration.myfaces;

import org.jsemantic.jembedded.integration.http.HttpEmbeddedTest;

public interface MyFacesEmbeddedTest extends HttpEmbeddedTest {
	void assertRuntimeConfigNotNull();

	void assertManagedBeanNotNull(String name);

	void assertNavigationRulesNotNull();

	void assertNavigationRuleNotNull(String fromViewId);

	void assertNavigationCasesNotNull(String fromViewId);

	void assertNavigationCaseNotNull(String fromViewId,
			String fromOutcome, String toView);
	
	void assertResourceBundlesNotNull();
	
	void assertResourceBundleNotNull(String name);
	
	void assertPropertyResolverNotNull();
	
}
