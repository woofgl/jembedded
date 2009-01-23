package org.jsemantic.jembedded.integration.myfaces;

import org.jsemantic.jembedded.integration.http.HttpEmbeddTest;

public interface MyFacesEmbeddTest extends HttpEmbeddTest {
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
