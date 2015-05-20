# Introduction #

The core container provides a set of annotations that allows to configure the different elements of the framework.

The annotations can be imported as separate module if needed, for example to create a new service that doesn't need the whole framework dependency.

Most of the annotations can be used at a method level and at a method level. Some even at a field level.

As it's been told before the container it's a composition of 1-n @Repositories, so most of the annotations refers to these repositories and to the container. Except the `@Container` annotation that refers only to the Container.


## @Repository Annotation ##

This annotation it's one of the most important ones, even though you don't need to use it if you don't want.

The jEmbedded container is a composition of 1-n Repositories:

**A @Repository is a holder for a collection of elements(services, components...) grouped logically or not**.

Obviously `@Repository` it's much more that just a holder, it's an IoC container of it's own as it has additional functionality and can instantiate, inject and manage the life cycle of the elements that it holds (specially Services as this a Service Oriented Container).

So by now it's clear that the basic element of `jEmbedded` it's the `@Repository`. In fact, you can create your own Container using them.

In this case `jEmbedded` is a group of repositories that can be configured as a hierarchy, for instance a hierarchy of layers (group of logical elements). For example, a `ServiceLayer` whose parent it's an `IntegrationLayer`.
The good about this approach it's that an element in the `ServicesLayer` can have access to elements in the `IntegrationLayer`, but not the other way.

As I said before you don't need to use this `@Repository` annotation and that's because one Repository it's created by default if any annotation it's given:

```
@WebClient
@PropertiesService

@Include(resources={WebServer.class})
public class IntegrationWebServerTest {

}
```

In the code above a `@Repository` is created for you, being the entry point to it the `IntegrationWebServerTest.class`.

The `@Include` annotation will add the resources to the `@Repository` and the custom annotations `WebClient and @PropertiesService` will have the same effect.

So the equivalent `@Repository` declaration would be:


```
@WebClient
@PropertiesService

@Repository(id="integrationWebServerRepo", resources=WebServer.class)
public class IntegrationWebServerTest {
}
```

Or you can just declare this way just using just the service class declarations:
```
@Repository(id="integrationWebServerRepo", resources={WebServer.class, 
HttpClient.class, PropertiesService.class})
public class IntegrationWebServerTest {
}
```

The advantage of creating a `@Repository` or more within the container it's the re usability and the grouping of elements in just one class. This way you can import the repository in another class (and therefore in another `@Repository`):

```
@Include (resource=IntegrationWebServerTest.class)
public class AnotherTest {
}
```

Furthermore you can create a hierarchy of repositories just using the repositories ids:

```
@Include (resource=IntegrationWebServerTest.class)
@Repository(id="anotherRepo", parent="integrationWebServerRepo")
public class AnotherTest {
}
```

This way the "anotherRepo" repository will have access to the "integrationWebServerRepo" elements.

### @Repository Annotation Attributes ###

| **Attribute** | **Description** | **Values** |
|:--------------|:----------------|:-----------|
| id            | repository id in the container |String      |
| resources     | elements the repository will hold |class       |
| parent        | parent repository reference | parent repository id in the container |
| iocProviders  | ioc container reference | collection of @SpringRepository reference|


- iocProviders:

As it's been said before the `@Repository` it's an implementation of the IoC pattern but an expanded one (with dynamic injection, annotations, etc..) but following the same principles.

There are a lot of IoC containers out there but it's very likely that you are using Spring   so I added native Spring integration to the container. In order to have access to it you will need to add the spring-support module to your project.

There are a few ways in which the container it's integrated with Spring and I'll explain that further in the proper section. In this section I'll explain the integration with the `@Repository`.

The integration of the `@Repository` with a Spring Context (`ApplicationContext`) it's made through the `@SpringRepository` annotation declaration. It can be used at a class level or method level.

`@SpringRepository`
| **Attribute** | **Description** | **Values** |
|:--------------|:----------------|:-----------|
| configurationFile| location of the Spring XML context file |String      |

This annotation will create a Spring Application Context with the given configuration file. This context it's treated as another `@Repository` by the container and that means you can have access to it:

```
Repository springRepository = embeddedHandler.getRepository("springRepository");

Object bean = springRepository.getBean("beanId");
```

So let's say you'd need that some of that Spring's beans to be injected or used in any of your services. That's a very easy thing to do, once the `@SpringAnnotation` is declared, add the reference to the @Repository that holds the services using the attribute `iocProviders`:

```
@SpringRepository(configurationFile = "embedded-database-service/persistence-layer.xml")

@Repository(id="invoicesService",  resources={EmbeddedDatabase.class}, 
		iocProviders = {"springRepository"})
@AbstractService(id="invoicesService", resources=InvoiceServiceImpl.class)
public interface InvoiceService {
}
```

Now you can reference any of the Spring beans in any Service of the @Repository:

```
@AnnotatedService(id = "invoicesServiceImpl", resources={RulesServiceImpl.class})
public class InvoiceServiceImpl extends AbstractCMTService implements
		InvoiceService {

	@Inject (ref = "customerDao") // this bean comes from the SpringRepository
	private CustomerDAO customerDao = null;	
}
```

This is the Spring context file:

```
<beans

<import resource="classpath:META-INF/invoices-service/properties-service/properties-service.xml"/>
           
<bean id="customerDao" class="org.jsemantic.services.examples.energy.invoice.dao.CustomerDAO" autowire="autodetect"/>	
</beans>
```

In order to inject the Spring beans in the `@AnnotatedService` you will need to use the
> the `@Inject` annotation (at a field level) like this:

```

@Inject (ref = "customerDao"), being the ref attribute the id of the bean in 
the Spring Context.
```


## @Inject Annotation ##

The `@Inject` annotation plays a main role in the composition process of the services (or other elements). With this annotation you can tell the container what elements of your service must be composed with other elements from the same container or an external one (Spring for instance).

## Injection by Type ##

The basic usage it's quite simple:

```
@Inject
private InvoicesService invoicesService = null;
```

The container will inject the instance of the `InvoicesService` that it should have in its repository. The drawback of using this kind of injection it's that you may only have one instance of the element in the container, as it will be found by type.

The annotation must be made at at attribute level. Note that you don't need the setter method in order to inject the element.


## Injection by Reference ##

This kind of injection lets you specify which element of the container you'd like to use
to inject any elements of your service. This kind of injection overcomes the limitations of the former by type, you don't need to have only one instance of the element in the container.

You only need to provide the id of the element in the container:

```
@Inject(ref="invoicesService")
private InvoicesService invoicesService = null;
```

You should use this method if you are injecting elements from another context (like Spring).

## Injection by Value ##

This a bit different as you won't be injecting references of other elements but values. This is useful for services that have properties that need to be configured, for example with a property file or just a fixed value.

### Case 1: Fixed values ###

This is a very simple example, but that is it how to inject a fixed value, just need to provide the value.

```
@AnnotatedBean(id="dependency")
public class TestDependency {
	
@Inject(value="hi")
private String msg = null;

public String getMsg() {
return msg;
}
}
```

### Case 2: Using the Properties Service ###

It's very unlikely that you are going to set fixed values in your service though, as it's much better if you externalize those values to a properties file (name/value).

In order to do this you can use one of the core services of `jEmbedded` the `PropertiesService` that will parse any property value that it's defined with the notation "${propertyLabel}":

```
@PropertiesService(propertiesFile="META-INF/web-server.properties")
public class JettyService {
```

`@PropertiesService`
| **Attribute** | **Description** | **Values** |
|:--------------|:----------------|:-----------|
| propertiesFile| location of the properties file in the classpath|String, compulsory|


The `@PropertiesService` needs the location of property file in the classpath. You need then to add the the annotation as usual in the repository space.

```
@PropertiesService(propertiesFile="META-INF/web-server.properties")
public class JettyService {

@Inject(value="${jettyService.port}")
private String port = "9005";
	
@Inject(value="${jettyService.host}")
private String host = "127.0.0.1";
	
@Inject(value="${jettyService.root}")
private String rootContext = "/";
}
```

### @Inject Annotation Attributes ###

| **Attribute** | **Description** | **Values** |
|:--------------|:----------------|:-----------|
| id            | tag id in the container |String, not compulsory |
| ref           | id of the element referenced in the container | String, not compulsory |
| value         | value of the attribute | String, value to inject or "${value}" |



## @Compose Annotation ##

The `@Compose` annotation plays a main role in the composition process of the services (or other elements). With this annotation you can tell the container what elements of your service must be composed with other elements from the same container or an external one (Spring for instance). The difference with the @Inject annotation is that @Compose links the life cycle of the composed service to the parent service. In other words, the composed service will be started and stopped by the parent, not by the container.

### @Compose Annotation Attributes ###

| **Attribute** | **Description** | **Values** |
|:--------------|:----------------|:-----------|
| id            | tag id in the container |String, not compulsory |
| ref           | id of the element referenced in the container | String, not compulsory |


`@Compose` creates then a composition and `@Inject` creates an association.

Note that `@Compose` it's only available for services and for reference.

## Compose by Reference ##

This kind of composition lets you specify which element of the container you'd like to use
to compose your service.

You only need to provide the id of the element in the container:

```
@Compose(ref="invoicesService")
private InvoicesService invoicesService = null;
```

You should use this method if you are injecting elements from another context (like Spring).