/* Adolfo Estevez Jimenez: aestevezjimenez@gmail.com
 * Copyright 2007-2009
 * http://code.google.com/p/jcontenedor,
 * http://code.google.com/p/jembedded,
 * http://code.google.com/p/jservicerules
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
package org.jsemantic.jintegration.test.myfaces.skeletal;

import java.util.Collection;

import org.apache.myfaces.config.RuntimeConfig;
import org.apache.myfaces.config.element.NavigationCase;
import org.apache.myfaces.config.element.NavigationRule;
import org.jsemantic.jintegration.test.http.skeletal.AbstractIntegrationHttpTest;
import org.jsemantic.jintegration.test.myfaces.MyFacesIntegrationTest;

public abstract class AbstractMyFacesIntegrationTest extends
		AbstractIntegrationHttpTest implements MyFacesIntegrationTest {

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
