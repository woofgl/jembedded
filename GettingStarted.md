

# Introduction #

An explanation about how to use the framework.

# Details #

Once you have the framework installed (see the install section) and the dependencies imported in your project you are ready to use the framework.

The way to use it will be very familiar to you if you have used JUnit before (I'm sure you did ;), specifically JUnit4 that's is annotation based.


## Steps to make an integration test unit ##

- Create a POJO class and annotate the class with the IntegrationTest Runner:


```

@RunWith(IntegrationTestClassRunner.class)

public class GenericIntegrationTest {

```

- Add the annotations you need in order to run the test (class level), for example if you'd need to test a web application you could add either a @JettyService or a @TomcatService. If you also need a embedded database you need to add the @DatabaseService annotation.

You can mix all the annotations (servers) you would need. For example you could make a integration test that uses the ESB, JMS, Database etc ..

All the annotations relies upon defaults, so you can use them right away. Anyway check the parameters that are available for each server (for example, for the http server the default port is 9006, but you can change with the property port. It's useful if the port is already being used or if you launch the tests in a parallel fashion as Hudson does).

```

@RunWith(IntegrationTestClassRunner.class)

@DatabaseServiceConfiguration

@JettyServiceConfiguration(webApplication="src/it/resources/webapps/test")

public class GenericIntegrationTest {

```

- Add the test methods you would need to complete your test. You need to put the @Test annotation in each method you want to be executed:

```

public class GenericIntegrationTest {
	
	@Test
	public void testWebApp() {

	}

	@Test
	public void testRequest() {
		
	}
}

```


- Import the assert test modules you'd need, in this case the http.Assert and the database.Assert.

The assert test modules are static classes that contains the methods provided to do the actual tests. The methods provided depends on the type of module, for instance the http.Assert contains methods to test the status response of a Http request (200, 500, 404 etc...).

As the framework use JUnit4 as a base you can also use the standard assert methods provided with it (assertNull, assertNotNull...).

```

//Assert Http Test Module.
import static org.jsemantic.jintegration.test.http.Assert.*;

//Assert Database Test Module.
import static org.jsemantic.jintegration.test.database.Assert.*;

@JettyServiceConfiguration(webApplication="src/it/resources/webapps/test")
        
        @Test
	public void testRequest() {
		String uri = "http://localhost:9006/test/index.html";
		//test a http request, if it does not return a status 200 (OK)
                //the test will throw an exception.
		assertRequestStatusOK200(uri);
                //test that the response returns something.
		assertContentNotEmpty(uri);
	}

        @Test
	public void testSelect() throws Exception {
                // test some sql operations.
		assertEqualsUpdateResult(
		"insert into test values(4, 'test4', 'test description 4')",
				1);
		assertResultNotNull("select * from test where id=4");
	}
}

```

- Launch the tests.

In order to do this you would need the JUnit4 runner. If you run the test from Eclipse you don't need to do anything special, just launch it like a junit test or make a test suite.

If you are planning to launch the test from maven you would need the surefire plugin (have a look at the pom's project).

And that's all :)








//Assert Http Test Module.
import static org.jsemantic.jintegration.test.http.Assert.*;

//Assert Database Test Module.
import static org.jsemantic.jintegration.test.database.Assert.*;

@JettyServiceConfiguration(webApplication="src/it/resources/webapps/test")
        
        @Test
	public void testRequest() {
		String uri = "http://localhost:9006/test/index.html";
		//test a http request, if it does not return a status 200 (OK)
                //the test will throw an exception.
		assertRequestStatusOK200(uri);
                //test that the response returns something.
		assertContentNotEmpty(uri);
	}

        @Test
	public void testSelect() throws Exception {
                // test some sql operations.
		assertEqualsUpdateResult(
		"insert into test values(4, 'test4', 'test description 4')",
				1);
		assertResultNotNull("select * from test where id=4");
	}
}

}}}```