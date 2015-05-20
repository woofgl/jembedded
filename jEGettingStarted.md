## Project Setup ##

Once you have installed the framework, you will need to declare the dependencies in your pom.

The core dependency (jembedded-core).
```
                 <dependency>
			<groupId>org.jsemantic</groupId>
			<artifactId>jembedded-core</artifactId>
                        <version>0.2.0-RC1</version>
		</dependency>
```

Any service dependency you are planning to use. For instance, if you want to use the jetty-service you would need to include:
```

                 <dependency>
			<groupId>org.jsemantic.services</groupId>
			<artifactId>jetty-service</artifactId>
                        <version>0.6.0-SNAPSHOT</version>
		</dependency>
```

Now that you have the framework classes available to your project, you can start using it right away.

## How to use jEmbedded ##

There are many ways you could use jEmbedded:

  * Business services container: create and manage your application business services, managing then in layers integrated with Spring (aop, tx, hibernate..).

  * Lightweight SOA to create SOA applications without using complex SOA suites.

  * Integration Container: as container for application integration, using an ESB, messaging services etc...

  * Rich Applications Container: integrated with GWT/Spring MVC to create RIA applications (SOA oriented).

  * Rich Portal Application Provider: introducing the concepts of Gwlets/Widglets to power RIA portals.


  * Web enviroment: can be used in any web enviroment, as a holder of a services layer for instance. Have a look at the case study  example as it shows the provided integration with SpringMVC.

  * Standalone container: implementing services (just one or a layer hierarchy), that you can use in any stand alone application (for example a Swing or a SWT based application).

  * Integrated with Spring: you can access Spring beans from jEmbedded using the native integration provided with the container or just create an `@SpringContext`

  * Easy prototyping: create services faster and test them with the integration test infrastructure included within the container and services.


### How to create a Service with jEmbeded ###

Please also have a look at the source code as I'm updating the documentation and examples.

There are many ways you can create services with jEmbedded. Let's start with a simple service "Hello World" but using the `@AbstractAnnotatedService` annotation that it's the most powerful annotation within the framework. Despite its name it's not only intended for abstract Services but for regular implemented Services as well.

Of course, you could use the `@AnnotatedService` for the latter ones but then you would need to extend the class using the `AbstractCMService` or the `AbstractService` abstract classes. You don't need to do that using the `@AbstractAnnotatedService` annotation. Keep in mind that the service returned it's a proxy class though.

## Tutorial 1: Hello World Service Example ##

1 - Create an annotation with the default configuration needed for your service (id, singleton, resources... inherited from the `@AbstractAnnotatedService` annotation) + your custom attributes. It's like when you are writing a Spring bean configuration XML file, but with the annotations advantages: more reusable and easier to use than a XML file.

```
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE, ElementType.METHOD })
public @interface HelloWorldAnnotatedService {
	 
         // service type to inherit
	Class<?> inherits() default AbstractAnnotatedService.class;
       
        //Inherited attribute
        // service id in the container
	String id() default "helloService";
        
        //Inherited attribute
        //other services or resources to load, none this time
	Class<?>[] resources();
        
        //custom msg attribute
        String msg() default="${helloWorldService.msg}";
      
        String propertiesFile() default "META-INF/hello-service/hello-service.properties";

}
```

2- In this case we have choosen the `@AbstractAnnotedService` but we'll  create an regular class as we don't need abstract methods but we don't want to extend any abstract class either.

```
@HelloWorld(msg="hello world")
public class HelloWorldService {
}
```

3- Add the attributes, methods etc you would need. In this case we need a String attribute and a method that retrieves the message.

```
@HelloWorld(msg="hello world")
public class HelloWorldService {

@Inject
private String msg;

public String getMessage(){
return msg;
}

}
```

The inject annotation will associate the msg attribute to the Service, populating it with the value from the custom annotation.

This value also can be populated using the properties service and a external properties file:

```
@Inject(val="${helloWorldService.msg"}
```

All the services has a pre-configured Properties Service available: META-INF/properties-service/properties-service.properties" but you can set up a custom one using the attribute propertiesFile:

```
 String propertiesFile() default "META-INF/hello-service/hello-service.properties";
```

Other option it's using the `AnnotatedPropertiesService` provided within the container, that will override all the previous configuration:


4- Create a test unit using the jEmbedded integrations testing support.

```
@RunWith(IntegrationTestClassRunner.class)//jEmbedded integration test runner
@Container(instanceType=ContainerInstanceType.PROTOTYPE_BY_THREAD)

@Include(resources = {HelloWorldService.class})//included resources to test
public class HelloWorldServiceTest {

@Test
public void test() {
	HelloWorldService service =(HelloWorldService) Assert.getService("helloService");
		
	assertEquals("hello world", service.getMsg());
        // this is also valid
        
        Service helloService = (Service)Assert.getService("helloService");
        assertEquals("helloService", service.getId());
}	
}
```

5- You can change the service class declaring it abstract and it will work fine, but now of course you could add abstract methods:

```
@HelloWorld(msg="hello world")
public abstract class HelloWorldService {

@Inject
private String msg;

public String getMessage(){
return msg;
}
}
```

6- A service can be started and stopped being both methods called by the container (as the service it's CMT) but you can override its behaviour using the `@Start` and `@Stop` annotations:

```
@AnnotatedHelloWorldService(msg = "hello world")
public abstract class HelloWorldService {
    @Inject
    private String msg;
	
    //to change the value, not needed for injection
    public void setMsg(String msg) {
         this.msg = msg;
    }

    @Start
    public void start() {
    	System.out.println("Start");
    }
    
    @Stop
    public void stop() {
    	System.out.println("Stop");
    }
    
    public String getMessage() {
    	return msg;
    }
}
```

In this case we are not doing anything very exciting but we'll use these methods to change the message on the fly:

```
@RunWith(IntegrationTestClassRunner.class)
@Container(instanceType=ContainerInstanceType.PROTOTYPE_BY_THREAD)

@Include(resources = {HelloWorldService.class, InterfaceHelloWorldService.class})
public class HelloWorldServiceTest {
	@Test
	public void test() {
		HelloWorldService service =(HelloWorldService)getService("helloService");
		
		assertEquals("hello world", service.getMessage());
		
		service.stop();
		service.setMsg("goodbye world");	
		service.start();
		
		assertEquals("goodbye world", service.getMessage());
	}
}
```