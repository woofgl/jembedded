**[jEmbedded Core Container](jEmbeddedCore.md)
  * [jEmbedded Core Annotations](JEmbeddedAnn.md)
  * [jEmbedded Services](jEServicesElements.md)**


# Introduction #
.
The jEmbedded framework it's build around the concept of a _Service Oriented Container_ or _SOC_
that provides an infrastructure of storage, management, execution and support for  services and other elements (such as components, beans.. ):

  * Identification: each element is identified by the container using an id. The elements can be singleton or prototype.

  * Storage using Repositories: a repository it's a bare container that can be composed in a hierarchical way, a single structure or a custom one. The `@Repository` is  the atomic element of the container.

The elements can be retrieved from these repositories at any moment through the container interface or using the helper class `ContainerHolder` or using  the `ContainerContext`.

  * Layer hierarchies: the services an other elements can be organized in a logical fashion using the `@Layer` annotation. It's a mix between a service and a repository.

  * Provides a set of core annotations (and classes) in order to create the elements: `@AbstractAnnotatedService`, `@AbstractAnnotatedComponent`, `@AnnotatedService`, `@AnnotatedComponent`, `@AbstractService` and `@AnnotatedBean`.

  * Management of the lifecyle (creation, init, start, stop, disposal) of the services and other elements.

  * Runtime and execution support: the container creates a context that can be accessed  from any given service allowing access to the container itself an its external enviroment (for example a Spring context).

  * Standard composition of services and elements: using 100% annotation configuration using any of the +20 services included or any custom one.

  * Dynamic creation and composition of services.

> - Standard IoC 100% annotated oriented: core annotations provided + custom ones (through annotation inheritance) in order to create, compose and configure the services or elements. Also you can use Spring as an additional IoC provider.

> -  Dynamic composition of services: `@AbstractAnnotatedService` that does not need implementation just an abstract class (or interface) + annotations and other services for composition.

> - The methods of  `@AbstractAnnotatedService` (or `@AbstractService`) can be implemented:

  * regular methods.
  * through composition of other services (`@ImplemenentedBy`)
  * through a BPM process: invocation of any number of services using a process file (`@ImplementedByBpm`).

> - Dynamic injection: the injection of services or other elements can be changed at runtime if needed.

  * Management of the services: the services can be accesed and managed through JMX (from any standard JMX console as jConsole).

- Additional features of the SOC container:

  * Lightweight SOA: integrated with MULE (as a service) + JMS, WS etc...

  * Business services Container: creating and managing a layer of business services (integrated with Spring, AOP, TX, Hibernate...).

  * RIA SOC container:

> - integrated with GWT 1.5+ through MVC Controllers: no need to use the RPC infraestructure (server).
> -  invoking services from the RIA infrastructure in a easy way.

  * Portal SOC container: introducing the concepts of Gwlet and Widglet, portlet style components implement with GWT/Sprinng MVC/jEmbedded Services.

  * Web application container: integrated with Spring/Spring MVC + Jetty and Tomcat Services.

## The Container ##

The container is the entry point to the framework and the instance you need to create to handle your application. Sometimes this instance will be created for you, depending on the enviroment you are working with (a web enviroment for instance).


The jEmbedded container is a composition of 1-n Repositories:

A `@Repository` is a holder for a collection of elements(services, components...) grouped logically or not.

Obviously `@Repository` it's much more that just a holder, it's a basic container and can create, inject and manage the life cycle of the elements that it holds (specially Services as this a Service Oriented Container).

So by now it's clear that the basic element of jEmbedded it's the `@Repository`. In fact, you can create your own container using them.

In this case jEmbedded is a group of repositories that can be configured as a hierarchy, for instance a hierarchy of layers (group of logical elements). For example, a `ServiceLayer` whose parent it's an `IntegrationLayer`. The good about this approach it's that an element in the `ServicesLayer` can have access to elements in the `IntegrationLayer` but not the other way around.

The jEmbedded Container it's represented by a handler that wraps it and can be obtained using a factory:

```
EmbeddedHandler container = EmbeddedHandlerFactory.getInstance(FooService.class);
container.start();
 
FooService  fooService = (FooService) container.getService("fooService");
```


`EmbeddedHandler`
| **Method** | **Description** |
|:-----------|:----------------|
| getInstance(classs:Classes[.md](.md))| Creates a container instance (handler) |
| getInstance(ctx:Context, Classes[.md](.md))|  Creates a container instance with a given context|
| getRepository(id:String):Repository| Retrieves a repository by Id) |
| getContext():Context| Creates the container Context |
| getBean(id:String):Object| Retrieves a bean by Id |
| getComponent(id:String):Component| Retrieves a component by Id |
| getService(id:String):Service| Retrieves a service by Id |
| getInstance(id:String):Object| Retrieve any instance by Id |
| containsInstance(id:String):boolean| Check if a instance exist within the container |

The handler represents the actual container (and all the repositories that contains) and can be used to retrieve any element: bean, service, component etc..

The factory needs an array of classes (or just one) that represents the elements the container will be handling. These elements are represented by it´s class or any other classes that may declare them.

In order a class to be recognised by the container it needs to declare any of the following annotations


- Repositories: `@Repository`.

- Layers: `@Layer`.

- Services: `@AbstractAnnotatedService, @AnnotatedService, @AbstractService`.

- Component: `@AbstractAnnotatedComponent, @AnnotatedComponent`.

- Beans: `@AnnotatedBean`.

- Core helper annotations: `@Include, @Inject, @Compose, @Start, @Stop, @Init, @Dispose`

- IoC provider annotations: `@AnnotatedSpringContainer`

- Provided Service annotations: `@AnnotatedJMService, @HttpServer`....

- Any annotations defined by you extending the core annotations.


### Container configuration ###

The container configuration it's quite simple and it's made through annotations (of course).

Basically you can setup how the container is going to be created: one per thread, a singleton or as many as you need per vm.

@Container
| **Attribute** | **Description** | **Values** |
|:--------------|:----------------|:-----------|
| instanceType:ContainerInstanceType | Type of container instance |PROTOTYPE\_BY\_THREAD, PROTOTYPE\_BY\_VM,  SINGLETON\_VM|

This annotation enables to configure the core container. Can be declared at a class level and only one annotation is allowed per container.

If no annotation is provided one is created by default:

`@Container(instanceType=ContainerInstanceType.PROTOTYPE_BY_VM)`

This means that a fresh container is created each time you ask for an instance using the handler factory:

```
@Container(instanceType=ContainerInstanceType.PROTOTYPE_BY_VM)

@AbstractAnnotatedService(id="webServer")
public class WebServer {

}

EmbeddedHandler handler = EmbeddedHandlerFactory.getInstance(WebServer.class);
handler.start();
```

If you need to have several containers in the same application this is the type to use. Every time a container is requires it's created from scratch and it's not associated with the current thread. Obviously this container can not be accessed through the `ContainerHolder` class.

Now let's suppose you need to create a fresh container every time a new thread is created and disposing the container when the thread is destroyed. For instance, if you are working in a Web enviroment and you need to associate the container instance to request thread.

As that container is associated with the current thread it can be retrieved using of course the declared handler but also from any other class using the class `ContainerHolder`:

```
EmbeddedHandler handler = ContainerHolder.getCurrentThreadContainer();
```

You have to be careful with this type of container though, because if you create another container in the same thread, the older one will be reused and you can get weird behaviour.

Finally, if only one instance of the container is needed for the whole application, you need to use the SINGLETON\_VM type. Once one instance is create and if another one is created an exception will be thrown (if the previous one hasn't been disposed). The `ContainerHolder` can be used to retrieve the container.


### Elements configuration: Layers, Repositories, Services, Components and Beans ###

jEmbedded gives you a lot of freedom to configure your elements (services etc..) as

```
@AnnotatedAbstractService(id="fooService")
public class FooService {
 
public String getMessage() {
  return "Hi I'm Foo Service";
}
}

EmbeddedHandler container = EmbeddedHandlerFactory.getInstance(FooService.class);
container.start();
 
Service fooService = container.getService("fooService");

```

And that would be all. Let's say now that you want to test this in a unit test:

```
@RunWith(IntegrationTestClassRunner.class)
@Container(instanceType=ContainerInstanceType.PROTOTYPE_BY_THREAD)

@Include(resources=FooService.class)
public class IntegrationContainerServiceTest {
             
              @Test
              public void test() {
                 assertExistService("fooService");
                           
                 FooService fooService = (FooService) Assert.getService("fooService");
                 fooService.getMessage();
              }
}
```


The only difference from the previous example is that we are using the `@Include` annotation instead of using the `FooService.class` directly.

As you can see the container configuration it's quite flexible and can be done in may differente ways. You can see the class space as the XML/XML-SCHEMA configuration file (like in a IoC container as Spring) where you can declare the resources and elements you would need.

You can also create your own annotations extending from the core annotations using the attribute inherits.

The difference it's that the "space" here it's more specific and the classes shouldn't be big sandboxes as the XML files usually are.

The annotation space covers all the class including class declaration level, fields and methods. You can use all the space o part of it, depends on your needs.

As a rule of thumb, class declaration level should annotated to indicate if the class it's going to be a service, component or bean and what kind. Also to include some general resources for the element.

Field annotation space could be used to inject or compose resources that the Service or other elements would need.

Methods annotations also can be used to declare resources, inject and compose.


```
<--- Annotation space (class declaration level)
@RunWith(IntegrationTestClassRunner.class)
@Container(instanceType=ContainerInstanceType.PROTOTYPE_BY_THREAD)

@WebClient
@Include(resources={FooService.class})
public class IntegrationContainerServiceTest {
 
```


```
<--- annotation space (class declaration level)
@WebClient
@Include(resources={FooService.class})
public class IntegrationContainerServicesTest {
 
@AnnotatedPropertiesService <--- Annotation space.
public Service getPropertiesService() {
}
```

Even the fields are included in the annotation space (only for `@Inject and @Compose`, though);

```
@WebClient
@Include(resources={FooService.class})<--- Annotation space (class declaration).
public class IntegrationContainerServicesTest {
 
@Inject <--- Annotation space (only for the @Inject or @ Compose annotations)
private FooService fooService = null;
```


All the annotations will result in services or elements added to the default `@Repository`, in this case a WebClient and the FooService.

The second configuration has a restricted Anotation space just to the classes added to the container directly:

```
handler = EmbeddedHandlerFactory.getInstance(FooService.class) <--- elements space
```

Of course you could do the following to expand the Annotation space:

```
@WebClient --> represents a custom @Annotation for a httpClient, it's the same if you'd add HttpClient.class
public class IntegrationContainerServicesTest {
 
handler = EmbeddedHandlerFactory.getInstance(FooService.class, IntegrationContainerServicesTest.class) <--- Annotation space
```

That means that you can use any class as Annotation space for your elements but it's better that any class declares the elements properly in a logical fashion, for instance a Layer class would declare a `@Layer` with it's resources,  a Service would declare @Service annotation of course. Other example would be a Test class that may declare any element or just a `@Repository`.

You should use a `@Repository` as entry point or a collection or logical elements, that can be reused. This would be the top element of the framework, A container 1-n Repo --> 1-n---> services --> 1-n components--> 1--n beans.

So let's see how would be look the former example a as `@Repository`:

```
@WebClient
@Repository (id="containerServices", resouces=FooService.class)<--- Annotation space (the whole class declaration)
public class IntegrationContainerServicesTest {
 
@Compose
private FooService fooService = null;

}
```

Well there is not much difference with the former configuration as in fact a `@Repository` was created by default, but declaring one `@Repository` yourself give  more control
> as you can access it by id from the container and compose a `@Repository` structure if needed.

```
Repository repo = handler.getRepository("containerService");
 
FooService service = (FooBean)repo.getService("fooService");
```


Let's see an example of a `@Layer`:
```
@Layer(id="servicesLayer, resources=FacadeService.class)
public abstract class ServicesLayer  {
             
     @Compose(ref="facadeService")
     public abstract FacadeService getFacadeService();
}
```


and the FacadeService:
```
@AnnotatedAbstractService(id="facadeService", resources=FooService.class)
public abstract class FacadeService  {
 
              @ImplementedBy(ref="fooService")
              public abstract String getMessage();
 
}
```

And a unit test:

```
@RunWith(IntegrationTestClassRunner.class)
@Container(instanceType=ContainerInstanceType.PROTOTYPE_BY_THREAD)

@Include(resources={ServicesLayer.class}) <--- Annotation space
public class IntegrationContainerServicesTest {
                     
              @Test
              public void test() {
                      ServicesLayer serviceLayer = (ServicesLayer)Asssert.getService("servicesLayer");
                      FacadeService facadeService = serviceLayer.getFacadeService();
                      String msg = facadeService.getMessage();
                      assertEquals("", msg);
              }
}
```

If you have a look at the complete example you would realize that you have only implemented the FooService and the test. This is a good example of how the dynamic composition works, you can use any service included in the jEmbedded framework or any you have implemented to compose on the fly new services.


### Container Context ###

The container creates a execution context that can be retrieved through the container handler or from any service or component.

The context allows to retrieve instances from the container without having to inject/compose in the Service. This could be useful in some situations when you need to change the injected instances at runtime.

Context
| **Method** | **Description** |
|:-----------|:----------------|
| getExternal():Object| Retrieves the external context if it exists, for instance a Spring Context |
| getInstance(id:String):Object| Retrieve any instance by Id |
| isWebContext():boolean| Check if the executing context is running in a web environment |

```
EmbeddedHandler container = EmbeddedHandlerFactory.getInstance(FooService.class);
container.start();
 
Context ctx = container.getContext();

Service fooService = ctx.getInstance("fooService");
```

You can let the container injects the context in your service in order to do that just add the Context as a reference:


```
@AnnotatedAbstractService(id="fooService")
public class FooService {

private Context context = null;

public void setContext(Context context) {
    this.context = context;
}

public String getMessage() {
  return "Hi I'm Foo Service";
}
}
```


### Container Test Infrastructure ###

jEmbedded provides a test infrastructure based on jUnit 4 (annotations) that will enable you to test the services created with the core container and also the provided services.

All the services contain static Assert classes with convenient methods that will help to test them, depending of its kind; for instance the container has methods to test if a service exist, the http client methods to test the http response etc... check every Assert class to learn what methods you could use.

The infrastructure lies within the container and the services, so you don´t need to import additional modules. I did this so the testing process would be integrated with the development. You would need to add the jUnit 4 jar as I left that as an optional dependency in the pom.xml.

Container static Assert
| **Method** | **Description** |
|:-----------|:----------------|
| assertExistService(id:String)| Checks if a service exists within the container |
| assertExistComponent(id:String)| Checks if a component exists within the container |
| assertExistBean(id:String)| Checks if a component exists within the containerd |
| getBean(id:String):Object| Retrieves a bean by Id |
| getComponent(id:String):Component| Retrieves a component by Id |
| getService(id:String):Service| Retrieves a service by Id |
| getContainer():EmbeddedContainer| Retrieves the container attached to the thread |

For instance:

```
import org.jsemantic.jembedded.container.test.Assert;

@Test
public void test() {
     assertExistService("fooService");                        
}
```

You can create the unit/integration test like any jUnit 4 test class but using a different runner and of course adding the needed resources.

jEmbedded Test Runner
`@RunWith(IntegrationTestClassRunner.class)`

The runner needs to create a container that is attached to the thread in order to make the static Assert works (so it could access the container elements). So in addition to declare the runner you need to add the right container configuration:

Container configuration for unit testing
`@Container(instanceType = ContainerInstanceType.PROTOTYPE_BY_THREAD)`

Adding the resources can be done in many ways, like if you were creating any other element with jEmbedded: using `@Include`, a `@Repository` etc....

```
//jEmbedded test runner
@RunWith(IntegrationTestClassRunner.class)

//The container must be associated to the actual thread.
@Container(instanceType = ContainerInstanceType.PROTOTYPE_BY_THREAD)

//The Service and other resources you'd need for the test
@Include(resources = { FooService.class })
public class TestFooService {

//Needed to create a fresh container per test method
//don't needed if you need to reuse the container between methods.
@After
public void dispose() {
 ContainerHolder.releaseCurrentThreadContainer();
}

@Test
public void test() {
   assertExistService("fooService");
}

@Test
public void testServiceMessage() {
   assertEquals("Hi I'm Foo Service", ((FooService)getService("fooService")).getMessage());
}
}
```