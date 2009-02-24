package org.jsemantic.jembedded.integration.myfaces.skeletal;

import java.util.Collection;

import org.apache.myfaces.config.RuntimeConfig;
import org.apache.myfaces.config.element.NavigationCase;
import org.apache.myfaces.config.element.NavigationRule;
import org.jsemantic.jembedded.integration.http.skeletal.AbstractHttpEmbeddedTest;
import org.jsemantic.jembedded.integration.myfaces.MyFacesEmbeddedTest;

public abstract class AbstractMyFacesIntegrationTest extends
		AbstractHttpEmbeddedTest implements MyFacesEmbeddedTest {

	public final static String MYFACES_RUNTIME_CONFIG = "org.apache.myfaces.config.RuntimeConfig";

	private RuntimeConfig getMyFacesRuntimeConfig() {
		return (RuntimeConfig) getHttpService().getServerContext().getAttribute(
				MYFACES_RUNTIME_CONFIG);
	}

	public void assertRuntimeConfigNotNull() {
		super.assertNotNull(getMyFacesRuntimeConfig());
	}

	public void assertManagedBeanNotNull(String name) {
		super.assertNotNull(this.getMyFacesRuntimeConfig().getManagedBean(name));
	}

	public void assertNavigationRulesNotNull() {
		super.assertNotNull(this.getMyFacesRuntimeConfig().getNavigationRules());
	}

	public void assertNavigationRuleNotNull(String fromViewId) {
		super.assertNotNull(getNavigationRule(fromViewId));
	}

	public void assertNavigationCasesNotNull(String fromViewId) {
		super.assertNotNull(getNavigationRule(fromViewId).getNavigationCases());
	}

	public void assertNavigationCaseNotNull(String fromViewId,
			String fromOutcome, String toView) {
		super.assertNotNull(getNavigationCase(fromViewId, fromOutcome, toView));
	}
	
	public void assertResourceBundlesNotNull() {
		super.assertNotNull(getMyFacesRuntimeConfig().getResourceBundles());
	}
	
	public void assertResourceBundleNotNull(String name) {
		super.assertNotNull(getMyFacesRuntimeConfig().getResourceBundle(name));
	}
	
	public void assertPropertyResolverNotNull() {
		super.assertNotNull(getMyFacesRuntimeConfig().getPropertyResolver());
	}
	
	public abstract void test() throws Exception;

	private NavigationRule getNavigationRule(String fromViewId) {
		Collection<NavigationRule> navigationRules = getMyFacesRuntimeConfig()
				.getNavigationRules();

		for (NavigationRule rule : navigationRules) {

			if (rule.getFromViewId().equalsIgnoreCase(fromViewId)) {
				return rule;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private NavigationCase getNavigationCase(String fromViewId,
			String fromOutcome, String toView) {

		NavigationRule navigationRule = getNavigationRule(fromViewId);
		if (navigationRule == null) {
			return null;
		}

		Collection<NavigationCase> navigationCases = navigationRule
				.getNavigationCases();
		if (navigationCases == null) {
			return null;
		}
		for (NavigationCase navCase : navigationCases) {
			if (navCase.getFromOutcome().equalsIgnoreCase(fromOutcome)
					&& navCase.getToViewId().equalsIgnoreCase(toView)) {
				return navCase;
			}
		}
		return null;
	}

}
