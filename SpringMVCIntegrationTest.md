

# Introduction #

To test a Spring MVC Web application you can use the general guidelines given in the how to start section:

- Create a POJO class that extends from IntegrationTest.

- Annotate the class with desired annotations (in this case a web server annotation would be right).

- Create the test methods and annotate them with @Test.

All the annotations relies on default values, except of course the minimum needed parameters. In this case would be webApplication(jetty) and webApplications(tomcat).

# Web Servers Annotations #

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


# Spring MVC Assert Module #

The assert test modules are static classes that contains the methods provided to do the actual tests. The methods provided depends on the type of module, for instance in this case the assert module is spring.Assert. It contains methods to test the spring mvc context and it's components. Also you can use the http.Assert in the same integration test (see example below).

As the framework use JUnit4 as a base you can also use the standard assert methods provided with it (assertNull, assertNotNull...).

## Methods provided ##

  * ApplicationContext getSpringApplicationContext()

  * assertExistComponent(String id)

  * assertExistService(String id)

  * assertExistBean(String id)

  * assertExistController(String id)


# Examples #

Several items are checked:  controller,  status http response etc..

```

//Http Assert Test Module.
import static org.jsemantic.jintegration.test.http.Assert.*;

//Spring Assert Test Module.
import static org.jsemantic.jintegration.test.spring.Assert.*;

@JettyServiceConfiguration(webApplication = "src/it/resources/webapps/spring")

public class EmbeddedSpringIntegrationTest extends IntegrationTest {
	@Test
	public void testContextPath() {
		assertNotNull(getWebApplicationContext());
		assertNotNullContextPath();
		assertEqualsContextPath("/test");
	}
	@Test
	public void controllersTesting() {
		String uri = "http://localhost:9006/test/controller";
		assertExistController("controller");
		assertExistBean("service");

		assertResponseStatusOK(uri);
		assertResponseContentEmpty(uri);
	}
}

```