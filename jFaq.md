# F.A.Q #


## Can jEmbedded be used in a web application? ##

Yes, for example integrated with SpringMVC. A `ContainerListener` is provided in the jembedded-spring-module for this matter. If you don't use Spring you can use a standard web listener.

Have a look a the invoicing-server for a complete example of this.


## What are the differences between @Container and @Repository annotations. ##

@Container is just an annotation to control how the instances are being created when `EmbeddedHandlerFactory.getInstance()` is invoked.

@Repository is a way to create logical collections of services, components etc, for example different layers of services or components (as you can create them as a hierarchy).

This is useful even when you don't want to create complex collections (as a tree of services) just as an entry point for the container:

```
@Repository (id="baseServicesRepo", resources={*.class})
public class RepoEntryPoint {

handler = EmbeddedHandlerFactory.getInstance(RepoEntryPoint.class);
...
}
```

Now for instance lets say you need to create a different group of services that need to reference the former repository for composition:

```
@Repository (id="extendedServices", resources={*.class}, parent="myRepo")
```

Now the new container would have access to the to the parent repository as well.


## What's the difference between the @Include and @Repository annotations? ##

With these two annotations resources can be added to the container but in a different manner as @Repository will create a new container for the resources and @Include will just add them to the current container (default container in case you haven't provided the @Repository annotation).

Use @Include if you only want to add some extra resources to the container and not to create a new one.