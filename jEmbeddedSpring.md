# Introduction #

jEmbedded it's a complete SOC container (IoC based) that you can use alone if you want or need because it provides a lot of different services that should cover most of the usual needs for a project (well, an Integration/lighweight SOA/RIA project).

But it's highly likely that you won't use this alone but with other frameworks as a service provider etc... and for sure one of this frameworks would be Spring as that would let you to use jEmbedded with other frameworks using Spring as a bridge (for instance, I integrated  jEmbedded with Mule this way because Mule configuration files are in fact Spring context files).

So I provided a few ways to integrate jEmbedded with Spring, one of them native through the jEmbedded context. In other words, jEmbedded integrates its context with the Spring Application Context allowing jEmbedded to have access to Spring beans and the Spring beans to the jEmbedded container. jEmbedded will use the Spring context for its injection process.

In order to do this you'd need to use the one of the exporters provided (Spring beans that export the jEmbedded container or its services into the Spring Context) so the jEmbedded container could access the Spring context.

The other way it's using a `@AnnotatedSpringRepository` annotation to which you specify the location of the Spring context file you'd like to use. Then you could inject any bean of the Spring context to the jEmbedded services using the @Inject(ref="springBeanId").


Note: the 0.2 RC1 version of jEmjebedded it's integrated with Spring 2.5.6 by default. You can chnage this modifying the pom files of course. The next version of jEmbedded will have the Spring 3.0 version by default so you can use the brand new injection annotations :)



## How to use the Spring integration ##

Even though the Spring integration it's native (as it's made through jEmbedded Spring context) I decided to put it in a different module just in case you are not using it:

```
                 <dependency>
			<groupId>org.jsemantic.jembedded</groupId>
			<artifactId>jembedded-spring-support</artifactId>
			<version>0.1-SNAPSHOT</version>
		</dependency>
```


Then you'd need to decide which way it's better for your purposes. If you'd already have a Spring context or a project that uses it maybe it's better to use the jEmbedded export container/services facility into the Spring context. This way Spring would act as the main container having access to the embedded services you just exported (or the whole jEmbedded container).

On the other hand if you are not using Spring or just need it in order to use one of it's facilities maybe it's better to use the `@SpringRepository` annotation and let jEmbedded be the main container. The Spring beans would be available to the jEmbedded services and resources using the @Inject annotation.

Finally, for the most complex cases, you'd need to use a mixed approach. For instance to use Spring TX mechanism with jEmbedded services. In this case you'd need to export the jEmbedded container and certain resources to the Spring context to make it work.. I added the ResorceExport for this matter. I will show this as an example of how you can make work  any Spring resource with jEmbedded.

## Exporting Services into the Spring context ##

Basically you need to put the jEmbedded services or resources into the Spring context and for that I provided a few configurations in the Spring way: as beans.

As usual you can find some reference configurations in the META-INF directory of the service, in this case it's a support module but still. These are only reference configurations you can use your own ones.

## Exporting the Container ##

If you'd like to have the jEmbedded container as a whole available for your Spring beans you'd need to use the `EmbeddedContainerRunner` bean (as it will start the container for you, not only will export it).

`EmbeddedContainerRunner bean`
| **Attribute** | **Description** | **Values** |
|:--------------|:----------------|:-----------|
| id            | Id in the spring context | handler    |
| useContext    | use Spring context or not | true/false |
| class         | Implementation class of the runner| org.jsemantic.jembedded.support.spring.EmbeddedContainerRunner|
| annotatedClasses | Classes to be processed by jEmbedded | any list of services|
| autoStarted   | Whether the container should be started | boolean, true by default|

For example we'd like to inject the `EmbeddedHandler` into a Spring bean:

```
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
		
	<bean id="handler" class="org.jsemantic.jembedded.support.spring.EmbeddedContainerRunner">
		<property name="annotatedClasses">
			<list>
				<value>org.jsemantic.service.SomeService</value>
				<value>org.jsemantic.service.AnotherService</value>
			</list>
		</property>
	</bean>

</beans>

```

And a Spring bean that injects it:

```

public class SomeSpringBean {

private EmbeddedHandler handler = null;

public void setEmbeddedHandler(EmbeddedHandler handler)
this.handler = handler;
}

public SomeService getSomeService() {
return handler.getService("someService");
}

public void doSomething() {

getSomeService().execute();
}
```

Please note the `useContext` attribute as it's very important. By default it's true and it will work as described, the jEmbedded container will use the Spring context for its injection process. This is fine for most part of the cases but could lead to some circular reference problems if you are using the mixed approach. For example you are exporting a resource from jEmbedded to the Spring context and at the same time the jEmbedded container will try to use this same resource as it's in the Spring context. In this case you should set the `useContext` to false.

In other word, if you export resources (with ResourceExport) you should block the jEmbedded context access to Spring and just let Spring take what it needs from jEmbedded. This not apply if you are just exporting Services with the ServiceExporter bean.

In the former example it's safe as you are exporting the container and the injecting it to a Spring bean.


## Exporting Services ##

You don't need to export the whole container, you can export a few services if you like using the `ServiceExporter" bean.

`ServiceExporter bean`
| **Attribute** | **Description** | **Values** |
|:--------------|:----------------|:-----------|
| bean id       | Service id in the Spring context | Service id |
| class         | Implementation class of exporter| org.jsemantic.jembedded.support.spring.exporter.ServiceExporter|
| annotatedClasses | Classes to be processed by jEmbedded | any list of services|
| property id   | Service id in the jEmbedded container | Service id |

For example, from the validation-service:

```
<bean id="validationService" class="org.jsemantic.jembedded.support.spring.exporter.ServiceExporter">
		<property name="id" value="validationService"/>
		
		<property name="annotatedClasses">
			<list>
				<value>org.jsemantic.services.validationservice.impl.ValidationServiceImpl</value>
			</list>
		</property>
	</bean>
```


And a Spring bean that injects it:

```
public class SomeSpringBean {

private ValidationService validationService = null;

public void setValidationService(ValidationService validationService)
this.validationService = validationService;
}

public void validate(Object obj) {
validationService.validate(obj);
}
```

In this case the jEmbedded container it's blocked from the Spring context.

## Exporting a Service Layer ##

If you had configured your services in layers (have a look at the jEmbedded examples and the case study) it's a good idea to export just the `ServicesLayer` and then injecting it into a Spring bean, for example in a Spring MVC controller. This way jEmbedded would act as services middleware. In order to do this (you could do using the services exported as well) you'd need to use the `ServiceLayerExporter`.

`ServiceLayerExporter bean`
| **Attribute** | **Description** | **Values** |
|:--------------|:----------------|:-----------|
| bean id       | Service Layer id in the Spring context | Service Layer id|
| class         | Implementation class of the layer exporter| org.jsemantic.jembedded.support.spring.exporter.ServiceLayerExporter|
| annotatedClasses | Layers to be processed by jEmbedded | any list of layers|
| exportedLayer | Layer id in the jEmbedded container | Layer id   |

For example, from the invoicing-server (case study, services-layer.xml):

```
<bean id="servicesLayer" class="org.jsemantic.jembedded.support.spring.exporter.ServiceLayerExporter">
		<property name="exportedLayer" value="serviceLayer"/>
		<property name="annotatedClasses">
			<list>
				<value>${layer.integration}</value>
				<value>${layer.services}</value>
			</list>
		</property>
	</bean>

<bean id="agentInvoicesService" class="org.jsemantic.jembedded.examples.invoicing.controller.InvoicesAgentService">
		<property name="servicesLayer" ref="servicesLayer"/>
</bean>

```

and the the Spring bean that injects it (it's a GWT controller):

```
public class InvoicesAgentService extends GWTController implements AgentGWTService {
	
	private ServicesLayer servicesLayer = null;

	public void setServicesLayer(ServicesLayer servicesLayer) {
		this.servicesLayer = servicesLayer;
	}

	public InvoiceDTO checkInvoices() {
		return servicesLayer.getInvoicesData();
	}

}
```

This way you could use jEmbedded as a provider of services using layers, and Spring for MVC etc etc..

Finally you can put all the configuration in one XML file and use the Spring tag import (from the mule configuration file from the case study):

```
<beans>
   <import resource="classpath:META-INF/invoices-service/invoices-service.xml"/>
   <import resource="classpath:META-INF/validation-service/validation-service.xml"/>
<beans>
```

## Exporting the Container & Resources: a mixed approach ##

As I said in the introduction there maybe be some times when you'd need more interaction between Spring and jEmbedded to make some Spring mechanisms work. For example the TX&AOP facility, you could apply this to a jEmbedded Service using an advice. But how you let Spring (and the AOP mechanism) access the resources that jEmbedded has created as a dataSource for instance?

For this you will need to use the `ResourceExporter` bean:

`ResourceExporter`
| **Attribute** | **Description** | **Values** |
|:--------------|:----------------|:-----------|
| bean id       | Resource id in the Spring context | Resource id|
| class         | Implementation class of the resource exporter| org.jsemantic.jembedded.support.spring.exporter.ResourceExporter|
|id             | Resource id in the jEmbedded container | Resource id|

### AOP&TX Spring Example with jEmbedded ###

Let's say you'd need to export a `DataSource` resource to the Spring context:

```
<bean id="handler" 
class="org.jsemantic.jembedded.support.spring.EmbeddedContainerRunner"> 
                <property name="useContext" value="false"/> 
                <property name="annotatedClasses"> 
                <list> 
<value>org.jsemantic.services.examples.energy.invoice.InvoiceService</value> 
                 </list> 
                </property> 
</bean> 

<bean id="dataSource" 
class="org.jsemantic.jembedded.support.spring.exporter.ResourceExporter" 
autowire="autodetect" depends-on="handler"> 
<property name="id" value="dataSource"/> 
</bean>
```

Now you can see why it's an mixed approach as you'd need to export the jEmbedded container first (it creates the DataSource resource) and then all the resources you'd like to Spring has access. On the other hand, the jEmbedded container will be blocked from the Spring context (note the `useContext` attribute set to false) so it won't have access to it.

And now let's say you'd need to apply the AOP&TX from Spring to the Invoices Service:

```
//continuing the previous file

<bean id="invoicesService" 
class="org.jsemantic.jembedded.support.spring.exporter.ResourceExporter" 
autowire="autodetect" depends-on="handler"> 
                <property name="id" value="invoicesService"/> 
</bean>

bean id="txManager" 
class="org.springframework.jdbc.datasource.DataSourceTransactionManager"> 
  <property name="dataSource" ref="dataSource"/> 
 </bean> 
 <tx:advice id="txAdvice" transaction-manager="txManager" > 
  <tx:attributes> 
   <tx:method name="get*" read-only="true"/> 
   <tx:method name="load*" read-only="true"/> 
   <tx:method name="select*" read-only="true"/> 
   <tx:method name="query*" read-only="true"/> 
   <tx:method name="criteria*" read-only="true"/> 
   <tx:method name="find*" read-only="true"/> 
   <tx:method name="generate*" read-only="false" propagation="REQUIRED" 
rollback-for="Exception"/> 
  </tx:attributes> 
 </tx:advice> 
 <aop:config> 
  <aop:pointcut id="serviceOperation" 
  expression="execution(* 
org.jsemantic.services.examples.energy..*Service.*(..))"/> 
  <aop:advisor pointcut-ref="serviceOperation" advice-ref="txAdvice"  /> 
 </aop:config>  
```

Of course you need to export the service to the Spring context to let the AOP mechanism creates a Proxy and the DataSource (we did this in the first step).

Now you can retrieve from Spring the proxied Invoices Service (not from the jEmbedded container!).

## Embedding a Spring context: using the `@AnnotatedSpringRepository` annotation ##

I've already explained how to export any resource you'd need to a Spring context from jEmbedded: services, container and resources. In that case you'd already had (most likely) a Spring context you wanted to integrate with jEmbedded. But there are times when you just need a helper Spring context to use it with jEmbedded (to implement a complete persistence layer with Spring, for instance),  and let jEmbedded inject any beans from it to its own services. For that matter you can use the `@AnnotatedSpringRepository` annotation.

@AnnotatedSpringRepository

| **Attribute** | **Description** | **Values** |
|:--------------|:----------------|:-----------|
| id            | Spring context service in the container| "springRepository" by default|
| configurationFile | location of the Spring configuration file| ${springRepository.configurationFile} by default|


Spring is the external IoC default provider used by jEmbedded, so you just need to add the `@AnnotatedSpringRepository` in any class to be used as an IocProvider by the jEmbedded container (so it can use the Spring beans to build the jEmbedded services or other.

```
@AnnotatedSpringRepository(configurationFile = "META-INF/invoices-service/embedded-database-service/persistence-layer.xml")
 
@Include(resources={EmbeddedDatabase.class, MockDataGenerator.class})
```

And the complete example (from the invoices-service example):

```

@AnnotatedAbstractService(id="invoicesService", resources={EmbeddedDatabase.class, MockDataGenerator.class})
public abstract class InvoiceService {
              
              @Compose
              @AnnotatedSpringRepository(configurationFile = "META-INF/persistence-layer.xml")
              public abstract SpringRepository getSpringRepository();


              @ImplementedBy(ref="invoicesService")
              public abstract Invoice generateInvoice(ReadingDTO dto);
             
}
```

In this case we use the `@AnnotatedSpringRepository` to create the persistence layer, daos, datasources etc...and then jEmbedded will inject some of that resources to its services (some are used by the `EmbeddedDatabase.class`).

In order to inject a Spring resource to a jEmbedded service or element you will need to use the `@Inject` annotation with the id of the bean (Spring resource):


```
@Inject (ref="dataSource") // ref is the id of the Spring Bean resource
private DataSource dataSource = null;
 
```



Also this annotation resolves into a jEmbedded Service that you could retrieve and use as usual:

```
@AnnotatedSpringRepository(configurationFile="META-INF/invoices-service/invoices-service.xml")
public class SpringTest {
 
private EmbeddedHandler handler = null;

@Before public void setUp() {
handler = EmbeddedHandlerFactory.getInstance(getClass());
handler.start(); 
}

@After 
public void release() {
handler.stop();
}
 
public void test() {
Repository repo = (Repository)handler.getService("springRepository");
InvoiceService invoiceService = (InvoiceService) repo.getService("invoicesService");
}
}
```

In this case the jEmbedded container won't use the Spring Context for its injection process, but you can use this context for other porpuses as testing etc...