All these examples can be found in the jEmbedded distribution: examples/services-examples. Also have a look at the Getting Started section. There you can find the `HelloWorldService` example.

The first 3 examples refers to the same service: Calculator Service created in different ways. The first 2 uses the `@AnnotatedService`that requires that you extend an abstract class and the third to the most advanced (and recommended) `@AbstractAnnotatedService` that will create a proxy for you and not requires to extend or implement any interface.




## Calculator Service Example 1. Using `@AnnotatedService` ##

So let's get started with a very simple service, just a calculator that sums and multiply two integer numbers. In order to do it more interesting and demonstrate how to compose services, the sum functionality will be provided by a external service: `SumService`. This service it's implemented in Ruby in fact, but forget this by now because it will be provided to you as a 3th party service.


### Steps ###

1 - jEmbedded as you will see in further examples gives you a lot of freedom and options to implement your services. We choose this time a simple way to do it, a regular implemented service handled by the container (CMT) In order to do that we can use the `@AnnotatedService` annotation:

```
@AnnotatedService()
public class CalculatorService extends AbstractCMTService {
	
}
```

Note that you need to extends an abstract class: `AbstractCMTService`. This is not needed if you use more advanced annotations like `@AbstractAnnotatedService`.

2 - Once the implementation class is created you need to complete the annotation with the needed attributes and resources you'd need to use:

```
@AnnotatedService(id="calculatorService", resources=SumService.class)
public class CalculatorService extends AbstractCMTService {
	
}
```

There are more attributes available (singleton, lazy etc..) but we'll discuss the 2 basic ones: id and resources.

- id: id of the service within the container. A string that will be used to identify and retrieve the service. In this case it's "calculatorService".

- resources: array of resources that the service will need (other services, components, beans etc). This resources are expressed using the resource class, that may be a class, and interface or a abstract class. In this case the service only a need a resource the `SumService=SumService.class`.

By default the resources will be handled and associated to the containe. To aggregate/associate or compose the resources to the service jEmbedded provides 2 annotations: `@Inject` and `@Compose`.

3 - Once the service has the resources available we need to decide how to handle them, let the container do it (then we need to do nothing) or the service (using `@Inject` or `@Compose`).

It makes sense that the `CalculatorService` owns (the resource) and handle the lifecycle of the `SumService`. When the calculator starts we need that the sum service it's started and when the calculator it's not longer needed neither is the sum service.

In order to do that we need to use the `@Compose` annotation. If we'd used the `@Inject` annotation, the sum service would be associated to the service but not its lifecycle (only the resource). The lifecyle would be handled by the container and there is no guarantee that the `SumService` would be started when the `CalculatorService` is.

```
@AnnotatedService(id="calculatorService", resources=SumService.class)
public class CalculatorService extends AbstractCMTService {


@Compose(ref="sumService")
private SumService sumService;
	
}
```

4 - Now we can add the business methods we need, in this case a sum and a multiply method:

```
@AnnotatedService(id="calculatorService", resources=SumService.class)
public class CalculatorService extends AbstractCMTService {

@Compose(ref="sumService")
private SumService sumService;
        
public int sum(int a, int b) {
        return sumService.sum(a,b);
}
	
public int multiply (int a, int b) {
	return a*b;
}
}
```

The sum method it's implemented using the composed service and the multiply method just coding it.

5 - Finally we can create a unit test. In order to do that you can use the jEmbedded test infrastructure:
```
//jEmbedded test runner
@RunWith(IntegrationTestClassRunner.class)

//The container must be associated to the actual thread.
@Container(instanceType = ContainerInstanceType.PROTOTYPE_BY_THREAD)

//The Service and other resources you'd need for the test

@Include(resources = CalculatorService.class)
public class CalculatorServiceTest {

//Needed to create a fresh container per test method
//don't needed if you need to reuse the container between methods.
@After
public void dispose() {
 ContainerHolder.releaseCurrentThreadContainer();
}

@Test
public void testSum() {
// retrieves the service using the static Assert
CalculatorService calculator = (CalculatorService) getService("calculatorService");
int sum = calculator.sum(30, 50);
assertEquals(sum, 80);
}
```


## Calculator Service Example 2. Using `@AnnotatedService` and a custom annotation ##

This example is the same that the previous one but instead of using the `@AnnotatedService` to annotate the service we'll create a custom annotation that inherits from `@AnnotatedService`. This is not required (as seen in the previous example) but it's a good practice to reuse, simplifly and organize the resources.


### Steps ###

1 - Create the custom annotation.

```
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE, ElementType.METHOD })
public @interface AnnotatedCalculatorService {

	//id of the service in the container
	String id() default "calculatorService";
        
        String clazz() default "org.jsemantic.jembedded.examples.services.ruby.CalculatorService";        

        // we are using an AnnotatedService and inheriting behaviour and
        // attributes.
	Class<?> inherits() default AnnotatedService.class;

	//resources we'll need to inject or compose
	Class<?>[] resources() default {SumService.class};	
}
```

It's quite similar to write a bean XML definition for a IoC container (like a Spring). You don't need to provide the Service class implementation as the container will do it for you.

When you code a custom annotation like this you are overriding the annotation definition you are inheriting from, in this case `AbstractAnnotatedService`:
```
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE})
public @interface AnnotatedService {
	
	String id() default "";
        
        String clazz() default "";            
    
	boolean isSingleton() default true;
	
	boolean isLazy() default false;

	Class<?>[] resources() default {};
}
```

If you don't override the attribute isSingleton() for example, the default will be used: true.

So check the annotation definition for any type you inherit.

2 - Annotate the service with the custom annotation.

```
@AnnotatedCalculatorService
public class CalculatorService extends AbstractCMTService {

@Compose(ref="sumService")
private SumService sumService;
	
}
```

3 - The rest remains the same as the previous example.


## Calculator Service Example 3. Using `@AbstractAnnotatedService` ##

The advantages of using the `@AbstractAnnotatedService` annotation it's that lets you to use a regular class or a abstract class but you don't need to extend any abstract class or implement any interface provided by the container.

It's more powerful than the `@AnnotatedService` as the container will provide for you implementation of some abstract methods (get methods and the ones that use the annotation `@ImplementedBy`) and you can mix abstract and and regular methods.

You don't need to implement the `@Service` annotation but you can actually cast the service to it if you need it.

Keep in mind that the container will provide you with a proxy not a regular implementation of a service when using this annotation. That can produce some use limitations and a little perfomance overhead.


### Steps ###

1 - Create the custom annotation.

```
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE, ElementType.METHOD })
public @interface AnnotatedCalculatorService {

	//id of the service in the container
	String id() default "calculatorService";
        
        // we are using an AbstractAnnotatedService and inheriting behaviour and
        // attributes.
	Class<?> inherits() default AbstractAnnotatedService.class;

	//resources we'll need to inject or compose
	Class<?>[] resources() default {SumService.class};	
}
```

2 - Annotate the service with the custom annotation.

```
@AnnotatedCalculatorService
public abstract class CalculatorService {

@Compose(ref="sumService")
private SumService sumService;
	
}
```

3 - Now we can add the business methods we need, in this case a sum (abstract) and a multiply method:

```
@AnnotatedCalculatorService
public abstract class CalculatorService {

@Compose(ref="sumService")
private SumService sumService;

@ImplementedBy(ref="sumService")        
public abstract int sum(int a, int b);
	
public int multiply (int a, int b) {
	return a*b;
}

}
```

4 - A service can be started and stopped being both methods called by the container or other service (as the service it's CMT) but you can override its behaviour using the `@Start` and `@Stop` annotations:

```
@AnnotatedCalculatorService
public abstract class CalculatorService {

@Start
public void start() {
   System.out.println("Start");
}
    
@Stop
public void stop() {
   System.out.println("Stop");
}

}
```

5 - The rest of steps remains the same as seen on previous examples.



## Mule Service Example 1. Using `@AbstractAnnotatedService` ##

To create this service we are gonna use an `@AbstractAnnotatedService`. This is not the only way to do it, jEmbedded gives you a lot of freedom and options to implement your services or other elements.  For instance, you could also create it using an `@AbstractService` (look at the next example) or just an `@AnnotatedService`.

The advantages of using the `@AbstractAnnotatedService` annotation it's that lets you to use a regular class or a abstract class but you don't need to extend any class or implement any interface.

In this case we are implementing the example provided with the Mule distribution Echo. It's a simple example, you provide a string and the service just echoes it. We are gonna use the services provide within jEmbedded, so you don't need to install or create the jars etc etc you'd normally use to test or create this example.


### Steps ###

1 - Well, we know what are going to do so the first step would be to consider with services we could use to compose our new service. In this case it's quite obvious: `MuleService` (to expose the service) and the `MuleClient` (to send messages to the service).

2 - Now you can create your custom annotation for  the service. This is not required, but it's a good practice to reuse, simplifly and organize the resources:

```
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE, ElementType.METHOD })
public @interface AnnotatedEchoService {

	//id of the service in the container
	String id() default "echoMuleService";
   
        // we are using an AbstractAnnotatedService, and inheriting behaviour and
        // attributes.
	Class<?> inherits() default AbstractAnnotatedService.class;

	//resources we'll need to inject (mule-service and client implementations)
	Class<?>[] resources() default {MuleServiceImpl.class,  MuleClientWrapperImpl.class};	
}
```

It's quite similar to write a bean XML definition for a IoC container (like a Spring). You don't need to provide the Service class implementation as the container will do it for you.

When you code a custom annotation like this you are overriding the annotation definition you are inheriting from, in this case `AbstractAnnotatedService`:
```
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE})
public @interface AbstractAnnotatedService {
	
	String id() default "";

	boolean isSingleton() default true;
	
	boolean isLazy() default false;

	Class<?>[] resources() default {};
}
```

If you don't override the attribute isSingleton() for example, the default will be used: true.

So check the annotation definition for any type you inherit.

3 - Once the annotation it's been created, we need to create the service class. You can choose between a regular class or an abstract class. In this case we choose and abstract class as we are going to use abstract methods:

```
@AnnotatedEchoService
public abstract class EchoMuleService {
```

Note that as we are using the custom annotation, the default attributes we provided apply (id, resources etc..). You can override them though:

```
@AnnotatedEchoService (id="echoMuleServiceMK2)
public abstract class EchoMuleService {
```


4 - When you are creating new services, keep in mind you should reuse and compose as much as possible (using the provided services or your own ones). In order to do that you have two available annotations (@Inject->associate or aggregation and @Compose-> composition).

In our example we have two resources to handle, the mule service and the client. We choose to compose the mule-service (its life cycle will be handled by our service, starting and stopping it, that is) and the client only associated to it (its lyfecycle will be handled by the container). In fact, the client in the real life would be located in another jvm, but for the sake of simplicity we'll do it this way.

You can choose to do nothing instead (neither compose or inject) so the resources would be handled by the container. But if you do that you can't access them using the abstract methods provided below:

```
@AnnotatedEchoService
public abstract class EchoMuleService {

//composed, referenced by it's service id. 
@Compose(ref="muleService")
public abstract MuleService getMuleService();

//injected	
@Inject(ref="muleClient")
public abstract MuleClientWrapper getMuleClient();
```

Note the abstract methods, that can be used to retrieved the service and client. These method will be implemented by the container and can be used in the regular methods implemented by the service.

5 - Once we have composed our service and we have available the resources we'd need we can implement the business methods of our service. We can do this using abstract methods that will be implemented by the container or just regular ones:

```
@AnnotatedEchoService
public abstract class EchoMuleService {
		
	//traditional way
	public Object sendEchoMessage(String echoEndPoint, String message) {
		return getMuleClient().sendMessage(echoEndPoint, message);
	}
	
	//could be implement this way as well, using an abstract method
	@ImplementedBy(ref = "muleClient", refMethodName="sendMessage")
	public abstract Object sendAbstractEchoMessage(String echoEndPoint, String message);
}
```

The two methods are identical. The difference it's that if you use an abstract method you need to provide the service and the method needed to implement the method.

6 - We'll need a final step to provide the needed configuration. In order to do that, jEmbedded provides a `@PropertiesService` that will load a properties file containing the service configuration:
```
@Compose(ref="propertiesService")
	@PropertiesService(propertiesFile="META-INF/mule-service/mule-echo-service.properties")
public abstract AnnotatedPropertiesService getPropertiesService();
```



7 - Finally we can create a unit test. In order to do that you can use the jEmbedded test infrastructure:
```
//jEmbedded test runner
@RunWith(IntegrationTestClassRunner.class)

//The container must be associated to the actual thread.
@Container(instanceType = ContainerInstanceType.PROTOTYPE_BY_THREAD)

//The Service and other resources you'd need for the test
@Include(resources = { EchoMuleService.class })
public class TestEchoMuleService {

//Needed to create a fresh container per test method
//don't needed if you need to reuse the container between methods.
@After
public void dispose() {
 ContainerHolder.releaseCurrentThreadContainer();
}

@Test
public void test() {
// retrieves the service using the static Assert
EchoMuleService echoMuleService = (EchoMuleService) Assert
				.getService("echoMuleService");
		
assertNotNull(echoMuleService.getMuleService());
		
String result = (String) echoMuleService.sendEchoMessage("vm://echo",
				"hello dude!");
assertEquals("hello dude!", result);
}
```


## Mule Service Example 2. Using `@AbstractService` ##


1 – Create an interface with the methods you would need.
```
public interface EchoMuleService {
   public Service getMuleService();
}
```

2 - Annotate the service at a class level with the `@AbstractService` annotation.

```
@AbstractService(id="echoMuleService", resources=MuleServiceImpl.class)
```

`@AbstractService`:

- id: service id in the container.

- resources: elements the service will use for composition, that will be handled by the container.

For example let's say you want to run a mule based service from your application, for instance the echo example that cames with the distribution just for testing.

Obviously you can do this from the mule directory etc.. but that's not easy and you can't import the example into your echo-application :)

For this matter you can just declare the mule service and client services dependencies in your pom:
```
                <dependency>
			<groupId>org.jsemantic.services</groupId>
			<artifactId>mule-service</artifactId>
			<version>0.1.0-SNAPSHOT</version>
		</dependency>
		<dependency>
			<groupId>org.jsemantic.services</groupId>
			<artifactId>mule-client-service</artifactId>
			<version>0.2.0-SNAPSHOT</version>
		</dependency>
```

All the services provided with jEmbedded follows the same packaging pattern,  they provide you with an interface, an implementation, some customs annotations and  a META-INF/xxx-service directory with the needed resources, usually a xxx-service.xml file(to use it from Spring or any other framework that uses its ApplicationContext), some properties files, some other configuration stuff etc... In fact this is the way you should package your services for distribution:

```
resources/
         META-INF/
                 xxx-service
                 /....config files
```

So basically to use a service you can:

**Import it using the attribute "resources" with a class/interface depending how it was designed. This way you can externalize the configuration of your service using the @PropertiesService: `resources=MuleServiceImpl.class`**

**Using its custom annotation:**

```
@MuleService(configurationFile="echo-config.xml")
public Service getMuleService();
```

The annotation can be declared at class or method level. If you want to have access to it, the best way it's to declare method that will implemented  by the service at runtime.

```
MuleService muleService = echoService.geMuleService();
```

Importing the service this way has the restriction that the configuration it's fixed on the annotation. This is not an issue when you want to develop and distribute a service like this one that is not likely to change.


3 - Annotate the methods and add the additional services you may need.

```
@AbstractService(id="echoMuleService", resources=MuleServiceImpl.class)
public interface EchoMuleService {
	
	@Compose(ref="muleService")
	public MuleService getMuleService();
	
	@PropertiesService(propertiesFile=
             "META-INF/web-server/mule-echo-service.properties")
	public void getPropertiesService();
}
```

Note the I added the `PropertiesService` as it's needed to parse the properties file configuration (you can avoid this using the custom annotation). This service is used to add functionality to the service but it's not managed by it. Also you don't need it to declare it as method (I did it for the sake of the example):

```

@PropertiesService(propertiesFile=
             "META-INF/web-server/mule-echo-service.properties")

@AnnotatedService(id="echoMuleService", resources=MuleServiceImpl.class)
public interface EchoMuleService {
	
	@Compose(ref="muleService")
	public MuleService getMuleService();
}
```

4- At this point we could think that would be nice to add a method that actually invokes the model that mule service exposes. This model just echo whatever string you sent to it so we'll add the following method:

```
public Object sendEchoMessage(String echoEndPoint, String message);
```

Now you may think that you have to write an implementation class.. but no you are wrong :), you can implement this functionality just using composition:

```
@ImplementedBy(ref="muleClient", refMethodName="sendMessage")
public Object sendEchoMessage(String echoEndPoint, String message);
```

`@ImplementedBy annotation:`

- ref: service or element that could implement this functionality (required).

- refMethodName: method of the ref element that needs to be invoked (optional). If it's not provided the declared method name will be used.

In this case we use the @MuleClient service to implement this method (with the paramas endpoint and message).

```
echoMuleService.sendEchoMessage("vm://echo", "hello dude!");
```

Finally the service looks like this:

```
@MuleClient
@AbstractService(id="echoMuleService", resources=MuleServiceImpl.class)
public interface EchoMuleService {
	
	@Compose(ref="muleService")
	public MuleService getMuleService();
	
	@PropertiesService(propertiesFile="META-INF/mule-service/mule-echo-service.properties")
	public void getPropertiesService();
	
	//We add custom functionality, using composition of elements
	@ImplementedBy(ref="muleClient", refMethodName="sendMessage")
	public Object sendEchoMessage(String echoEndPoint, String message);
}
```

And a stripped version:

```
@AbstractService(id="echoMuleService", 
resources={MuleServiceImpl.class, MuleClientWrapperImpl.class,PropertiesService.class}
public interface EchoMuleService {
		
	@ImplementedBy(ref="muleClient", refMethodName="sendMessage")
	public Object sendEchoMessage(String echoEndPoint, String message);
}
```

If you use this version you also will need add the configuration in a properties file in META-INF/repository.properties.

As you can see jEmbedded gives a lot of flexibility in order to configure and compose the services or other elements as components or beans.


4 – Now you have the service to ready to go, but how still you need and environment to run it.


For example running it from a test unit:

```
//note if you not declare the @Respository annotation here, a default repository will
//be created

@RunWith(IntegrationTestClassRunner.class)
@Container(instanceType=ContainerInstanceType.PROTOTYPE_BY_THREAD)

@Include(resources=EchoMuleService.class) // include the service into the container
public class TestEchoMuleService {
	
	@Test
	public void test() {
		
		EchoMuleService echoMuleService =(EchoMuleService)Assert.getService("echoMuleService");
		
		String result = (String)echoMuleService.sendEchoMessage("vm://echo", "hello dude!");
		assertEquals("hello dude!", result);
	}
}
```

5 - Now you can run the service for real using with the jEmbedded plugin.

This plugin is for maven so you need to declare it in your pom:

```
	<plugins>
		<plugin>
		<groupId>org.jsemantic.jembedded</groupId>
		<artifactId>jembedded-plugin</artifactId>
		<version>0.2-SNAPSHOT</version>
		<configuration>
			<argLine>-Xms256m -Xmx512m</argLine>

				<additionalClasspathElements>
					<additionalClasspathElement>${basedir}/target/classes</additionalClasspathElement>
				</additionalClasspathElements>	
				<useSystemClassLoader>true</useSystemClassLoader>
					<container>
						<id>test</id>
						<classes>
							<class>org.jsemantic.jembedded.examples.services.mule.EchoMuleService</class>
							
						</classes>
					</container>
				</configuration>
		</plugin>
	</plugins>
```

To start the container: `mvn -o jembedded:start`

Once the container it's started, you will prompted to enter something, just do it and you will have your echo, cool huh? ;)

6 - Packaging and distribution.

Now that you have a complete service you may think that you could distribute or reuse it using composition.

In order to this you need to do:

- Create a pom (have a look at any pom provided with the jembedded-services) that will package the classes and resources in a jar.

- Create a custom annotation if you think that could be useful for the users of the service:
```
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE, ElementType.METHOD})
public @interface EchoMuleService {
	
        String id() default "echoMuleService";
        
        Class<?> inherits() default AbstractService.class;
	
        //you could declare more properties if needed
}
```

This way anyone that wants to use the service only will need to declare the annotation (that it's using jEmbedded that is):

```
@EchoMuleService // include the service into the container
public class TestEchoMuleService {
	
..
}
```

- Create a META-INF/echo-mule-service directory and include a properties file for configuration (if needed) and a xml beans file if you are planning to use this service from Spring or any framework that uses Spring's ApplicationContext (as such as Mule):
```
<bean id="echoMuleService" class="org.jsemantic.jembedded.support.spring.exporter.ServiceExporter">
		<property name="id" value="echoMuleService"/>
		
		<property name="annotatedClasses">
			<list>
	<value>org.jsemantic.jembedded.examples.services.mule.EchoMuleService</value>
			</list>
		</property>
	</bean>
```

To use from Spring just use the import tag:

`<import resource="classpath:META-INF/echo-mule-service/echo-mule-service.xml"/>`