

# Introduction #

To test a web application you can use the general guidelines given in the how to start section:

- Create a POJO class that extends from IntegrationTest.

- Annotate the class with desired annotations (in this case a web server ann would be right).

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


```

//Assert Http Test Module.
import static org.jsemantic.jintegration.test.http.Assert.*;

@JettyServiceConfiguration(webApplication="src/it/resources/webapps/test")

public class WebIntegrationTest extends IntegrationTest {
	
	@Test
	public void testContextPath() {
		assertNotNull(getWebApplicationContext());
		assertNotNullContextPath();
		assertEqualsContextPath("/test");
		assertNotNull(getHttpClient());
	}

	@Test
	public void testRequest() {
		String uri = "http://localhost:9006/test/index.html";
		
		assertRequestStatusOK200(uri);
		assertContentNotEmpty(uri);
	}
	
	@Test
	public void testPost() {
		String uri = "http://localhost:9006/test/index.html";
		assertContentNotEmpty(uri);
		assertPostStatusOK200(uri);
	}
	
	@Test
	public void testError() {
		String uri = "http://localhost:9006/test/noexists.html";
		assertRequestStatusNotFound404(uri);
	}
}
```


## Tomcat Web Server Annotation ##

@TomcatServiceConfiguration(webApplications, port, host, root)

  * _port:_ embedded web server listener port (9006 by default).

  * _root:_ web application root context (/test by default).

  * _webApplications:_ actual folder location of the web applications (needed).

  * _host:_ application host (by default localhost).


```

@TomcatServiceConfiguration(webApplications = "src/it/resources/webapps")

public class IntegrationTomcatTest  extends IntegrationTest {

	@Test
	public void testContextPath() {
		assertNotNull(getHttpClient());
	}

	@Test
	public void testRequest() {
		String uri = "http://localhost:9006/spring/controller";
		assertRequestStatusOK200(uri);
	}
	@Test
	public void testPost() {
		String uri = "http://localhost:9006/spring/controller";
		assertPostStatusOK200(uri);
	}
	
	@Test
	public void testError() {
		String uri = "http://localhost:9006/spring/notexists";
		assertRequestStatusNotFound404(uri);
	}
	
}

```


# Web Assert Module #

The assert test modules are static classes that contains the methods provided to do the actual tests. The methods provided depends on the type of module, for instance in this case the assert module is http.Assert. It contains methods to test the status response of a http request (200, 500, 404 etc...) and many more.

As the framework use JUnit4 as a base you can also use the standard assert methods provided with it (assertNull, assertNotNull...).


## Provided Methods ##

  * assertEqualsContextPath(String contextPath)

  * assertNotNullContextPath()

  * assertInitParameterNotNull(String initParameter)

  * assertContentNotEmpty(String uri)

  * assertRequestStatusOK200(String uri)

  * assertRequestStatusAccepted202(String uri)

  * assertRequestStatusPartialInformation203(String uri)

  * assertRequestStatusNoResponse204(String uri)

  * assertRequestStatusBadRequest400(String uri)

  * assertRequestStatusUnauthorized401(String uri)

  * assertRequestStatusPaymentRequired402(String uri)

  * assertRequestStatusPaymentForbidden403(String uri)

  * assertRequestStatusNotFound404 (String uri)

  * assertRequestStatusInternalError500 (String uri)

  * assertRequestStatusNotImplement501 (String uri)

  * assertPostStatusOK200(String uri)

  * assertPostStatusCreated201(String uri)

  * assertPostStatusNoResponse204(String uri)

  * assertPostStatusBadRequest400(String uri)

  * assertPostStatusUnauthorized401(String uri)

  * assertPostStatusPaymentRequired402(String uri)

  * assertPostStatusPaymentForbidden403(String uri)

  * assertPostStatusNotFound404 (String uri);

  * assertPostStatusInternalError500 (String uri)

  * assertPostStatusNotImplement501 (String uri);

  * assertResponseNotNull(String uri);