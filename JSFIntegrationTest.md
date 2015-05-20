# Introduction #

To test a JSF Web Application you can use the general guidelines given in the how to start section:

- Create a POJO class that extends from `IntegrationTest`.

- Annotate the class with desired annotations (in this case a web server annotation would be right).

- Create the test methods and annotate them with @Test.

All the annotations relies on default values, except of course the minimum needed parameters. In this case would be webApplication(jetty) and webApplications(tomcat).


# JSF/Web Servers Annotations #

There are two embedded web servers to choose from:

  * Jetty : @JettyServiceConfiguration(webApplication, port, host, root)

  * Tomcat: @TomcatServiceConfiguration(webApplications, port, host, root).


## Jetty Web Server Annotation ##

@JettyServiceConfiguration(webApplication, port, host, root)

  * _port:_ web server listening port (9006 by default).

  * _root:_ web application root context (/test by default).

  * _webApplication:_ actual folder location of the web application (needed).

  * _host:_ application host (by default localhost).


Can be ran as a standard JUnit4 test from Eclipse, Maven, Hudson etc...


## Tomcat Web Server Annotation ##

@TomcatServiceConfiguration(webApplications, port, host, root)

  * _port:_ embedded web server listener port (9006 by default).

  * _root:_ web application root context (/test by default).

  * _webApplications:_ actual folder location of the web applications (needed).

  * _host:_ application host (by default localhost).


# JSF Assert Module #

The assert test modules are static classes that contains the methods provided to do the actual tests. The methods provided depends on the type of module, for instance in this case the assert module is jsf.Assert. It contains methods to test the JSF context.

As the framework use JUnit4 as a base you can also use the standard assert methods provided with it (assertNull, assertNotNull...).


## Provided Methods ##

  * assertRuntimeConfigNotNull()

  * assertManagedBeanNotNull(String name)

  * assertNavigationRulesNotNull()

  * assertNavigationRuleNotNull(String fromViewId)

  * assertNavigationCasesNotNull(String fromViewId)

  * assertNavigationCaseNotNull(String fromViewId, String fromOutcome, String toView)

  * assertResourceBundlesNotNull()

  * assertResourceBundleNotNull(String name)

  * assertPropertyResolverNotNull()


# Examples #

- A Integration test unit for JSF. In this case, the creation of a managed bean is tested: ("testBean").

```

import static org.jsemantic.jintegration.test.myfaces.Assert.*;

@JettyServiceConfiguration(webApplication="src/main/resources/webapp/myfaces")

public class IntegrationMyFacesEmbeddedTest extends IntegrationTest {

	@Test
	public void ManagedBeanCreationCheck() throws Exception {
		assertRuntimeConfigNotNull();
		assertManagedBeanNotNull("testBean");
	}
}

```