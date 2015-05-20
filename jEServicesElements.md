## jEmbedded Services and Other Elements ##

Please also refer to the source code as part of this page it´s not up-to-dated.

The jEmbedded container can handle many different elements as Services, Abstract Services, Components and Beans. All of them can be created and managed through annotations.

Can be deduced from the name of the framework that there is one kind of "special element" and that it's the embedded service:

**Standalone service that can be invoked from within the same VM**

Many of the provided services within the container are embedded ones: JMS-Broker, Mule-Service, CFXServer-Service etc... most of that services can be used as no embedded as well, but I have prepared then (in some cases they already had an embedded implementation) and packaged them to be embedded so it can be used directly from the container, saving you the pain to install them, configuring them (well some little config it's needed, just the basic one from the annotations) etc... For example, you don't need to put jar's or classes in the classpath in order to the server to load them, or deploy a war etc etc..

### Services ###

Usually offers functionality to any potential clients (for instance, an application or other service), implementing an interface (or not) that provides the contract for the client.

All the Services created using the implements the interface `Service` meaning that

## Container Managed implemented Service ##

The  Container Managed Service, it means that the container will managed it's lifecyle (init, start, stop, dispose).


This is the standard service, implementing an interface and extending an abstract class to  get its functionality(this is changing and in the next version of `jService` there won't be any need of abstract classes, won't be deprecated though).

```
@AnnotatedService(id="jdbcService", resources=DataSource.class
public class JdbcServiceImpl extends AbstractCMTService implements
		JdbcService {

        @Compose(ref="jdbcService)
	private JdbcTemplate jdbcTemplate;
	
	@Inject(ref="dataSource")
	private DataSource dataSource = null;
```

This is a Container Managed Service, it means that the container will managed it's lifecyle (init, start, stop, dispose).

The service can have some resources associated to it and injected but still are managed by the container (`DataSource.class`). Still the service can managed other services with the attribute `managedElements" handling its life cycle himself.

One of the benefits of the managed elements it's that as they don't live alone they have access to their environment and maybe to other's. The container injects a Context to the service (and the components as well), that provides access to the executing enviroment and the container itself. It means you can have access to other services or elements within the service:

```
JdbcService jdbcService = handler.getService("jdbcService");
jdbcService.getContext().getInstance("id");
```

or if you are executing the the container in other container environment as such as Spring
you can have access to it:

```
JdbcService jdbcService = handler.getService("jdbcService");
ApplicationContext ctx = (ApplicationContext)jdbcService.getContext().getExternal();
```

A service can manage: services, components, beans  and entities.

## Container Managed Abstract Service ##

This service would the same as the former if it wasn't because you don't need to supply an implementation for it, just and interface + some annotations. That's the reason why it's an `AbstractService`:
```
@AnnotatedService(id="webServer", resources=JettyServiceImpl.class)
public interface WebServer extends Service {
	
	//This service one is managed by the WebServer, started and stopped by it.
	@Compose(ref="httpService")
	public HttpService getHttpService(JettyService httpService);
	
	//This service is just for composition, but its lifecycle is managed by the container, not the WebServer
	@PropertiesService(propertiesFile="META-INF/web-server/web-server.properties")
	public PropertiesService getPropertiesService();
	// end of service composition
	
	//Added functionality, you can create dynamic invoking methods from any service
	//that you may have in the container
	
	@ImplementedBy(ref="httpService", refMethodName="getServerContext")
	public ServletContext getServletContext();
	
	@ImplementedBy(ref="httpService")
	public void setPort(String port);
	//end of added functionality
}
```

The annotation is likely to change in the next release to `@AbstractService`. Have a look at the "5 minutes guide" for a good example of an `AbstractService`.


### Components ###

Provides support functionality to services or other components but usually it's not invoked directly by any client but for other services or components.

- All the components should implement the interface `Component`.

- A component can manage: component, beans and entities.

## Container Managed Component ##

This element's life cycle is managed by the container, which is different from service's one:

- init (the component it's initialized, the method `postConstruct` can be overrided)

- dispose (the component is discarded, the method release can be overrided).

A component therefore can't be started/stopped. As this is a CMT component the former methods can only be invoked by the container.

In order to implement a CMT Component the `AbstractCMTComponent` must be extended, this will change in the next version of `jService` (no abstract classes needed at all).

For example:

```
@AnnotatedComponent(id="supportComponent")
public class SupportComponent extends AbstractCMTComponent {
	
	@Override
	protected void postConstruct() {
		System.out.println("PostConstruct");
	}
	
	@Override
	protected void release() {
		System.out.println("Release");
	}
	
	public String support() {
		return msg;
	}
```


## Simple Component (non managed) ##

The principal difference with the former is that the life cycle of the component must be handled by any other service or component, invoking it's methods directly. The container only will instantiated it.

In order to implement a Simple Component the `AbstractComponent` class must be extended, this will change in the next version of `jService` (no abstract classes needed at all).


### Entities ###

Just a pojo, but does not follow the get/set bean standard. Usually are DOM classes, utility classes, etc...

jEmbedded can produce definition, make instances and hold any number of entities that are annotated with a minimal configuration. In this case the lifecycle of the entity is managed by the application nor the container (for example, could be diposed by a parent componen).

```
@AnnotatedBean(id="dependency")
public class TestDependency {
	
	@Inject(value="hi")
	private String msg = null;
}
```

An entity can manage entities or beans.

### Beans ###

Just a pojo that follows the getter/setter standard but that it's created and stored by the container.

It doesn't have an special life cycle beyond the creation and garbage collected.

## Annotated Bean ##

It' enough in order to create a bean to annotated and inject some values or dependencies.

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