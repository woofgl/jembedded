# Case study: Electrical Measures & Invoicing #

So let's say we need to build a quick prototype for the company "Electrical & CO" that wants to implement a new system that will capture the electrical readings in every building and then send them to a central system that will calculate and print the invoices.

In order to connect to the Central System, we'll need a device in every building that gets the readings and send them through the internet.

We can think about that device as an embedded pc system with an IP and an id that will connect to the internet and send the data to the central system through a few protocols. This device will have to get the measures from every home, so we'd still new some electrical meters in place and connect them to the embedded device.

As the "Electrical Co" already has a Invoicing System and doesn't want to replace it, we'll have to use and connect it to the new system. It has a java api to invoke, but no WS api or JMS ect.... therefore we'll have to come up with something that allows to connect to the invoicing system but at the same time that give us flexibility and don't tie the new system to it.

It seems that we got a pretty clear case of System's Integration so using an SOA/Integration architecture is in place.

So how do we build the proyotype? We can just get the ESB and a bunch of frameworks and start integrating then, etc... but that takes time and certainly don't promote reuse (it's a prototype but still), so why don't use a ready framework for that, that implemens service composition, that has ready to go services, promotes reuse and eases all the integration pain? :)


## Prototype ##

First we can define the protocols we'll use to connect to the central system. We could think that JMS over HTTP will be the safest one to use but for the purposes of the prototype we'll use plain HTTP protocol in order I can show you the jEmbedded/Spring/Spring MVC integration.

Now with the description of the system at hand we can came up with the services, components etc... that we'd need, trying to use as much service composition as possible:

  * Meter reader agents: they will take the measures and send them to the Meter-Hub system every x days. We'll model them as agents that will connect to the Meter-Hub through JMS (using the JMS client) sending semi random data. As we have an agent-service available we'll use it.

  * Meter-Hub: aggregate all the data collected by the meter readers and send them via HTTP (we'll use the HTTP-Client component for that).

  * Central System: this system will receive the requests from the meter-hub (using a Spring MVC controller, for the example porpuses) and will invoke the Invoicing system.

We'll model the Central System as a layer of Services that also will have a parent Integration Layer (this layer will be composed of a MULE ESB and the Invoicing System).


### Entry Points ###

There will be 1 entry point to the Central System: an HTTP-Entry point represented by a MVC controller (it's url, in fact).

In order to model the entry point we'll need a web-server that we can implement using a jetty-service and to implement the controller we'll use SpringMVC that it's supported by jEmbedded natively.


| **Entry Point** | **Model** | **Implementation** |
|:----------------|:----------|:-------------------|
| HTTP-Endpoint   | Web/Controller | Jetty-Service/Spring MVC |

### Services ###

Apart from the services needed to implement the entry points we'll need some business services and other components to implement the application work flow.

It's a good idea to group the application services in a logical fashion, in other words in layers:

- A Service Layer that will work as a facade for the system. It's a good idea that the external clients (whatever they might be) access just a facade that shields it from changes and don't expose more than the necessary interface.

The Service Layer will be composed as a hierarchy having as a parent the Integration Layer. This way the Service Layer will have access to the services from the Integration Layer but the Integration layer won't have access to the Services Layer.

- A Integration Layer that's the interface to the external systems, in this case it's the Invoicing-Server that's is modelled by composition of a few services.


## Application Flow. ##

The requests sent by the Meter-Hub will be received by the HTTP entry point (the Spring MVC Controller).The controller will be invoked receiving the two parameters needed to calculate the invoice. The controller has a reference to the Service Layer and its facade invoking the Invoicing System.

The Service Later is implemented using the `@Layer` annotation that it's a Service itself (well, more like a repository). It has a method `generateInvoice` that basically retrieves generates a DTO with the received data (reading) and sends it to the Invoicing-Service using JMS:

```
public void generateInvoice(String customerId, String kwh) {
    ReadingDTO dto = dtoFactory.getInstance(customerId, kwh);
    getJmsClient().sendObjectMessage("in.queue", dto);
}
```


Once the Invoicing-Server receives the DTO in it's inbound queue it's validated by the Mule-Validation-Service. If the data it's wrong, it will dispatched to a error queue. If it's correct it will be dispatched to the Invoices-Service, that will generate the invoice, will send it to the outbound queue and store the invoice in a xml format in a directory.

## Implementation ##

The implementation of the prototype will be made using as little implementation as possible using composition with abstracts services.


### Services Layer ###

```
@AnnotatedJmxServer
@AnnotatedServicesLayer
public abstract class ServicesLayer {
	
	@Inject(ref="dtoFactory")
	private DtoFactory dtoFactory;
	
	@Inject
	@AnnotatedJmsClient(brokerUri="tcp://localhost:61618")
	public abstract JmsClient getJmsClient();
	
	public void generateInvoice(String customerId, String kwh) {
		ReadingDTO dto = dtoFactory.getInstance(customerId, kwh);
		getJmsClient().sendObjectMessage("in.queue", dto);
	}
	
}
```


The controller looks like this:

```

public class Controller extends AbstractController {

	private ServicesLayer servicesLayer = null;

	public void setServicesLayer(ServicesLayer servicesLayer) {
		this.servicesLayer = servicesLayer;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String id = (String) request.getParameter("id");
		String kwh = (String) request.getParameter("kwh");
		servicesLayer.generateInvoice(id, kwh);
		return null;
	}
}
```

The controller invokes a method of the the Service Layer that will be generate the invoice.


### Integration Layer ###

The integration layer it's the interface to the external systems, in this case it's the invoices-service that's is handled by a MULE ESB.

The invoices-service doesn't get all the requests upfront, there is queue that gets all the petitions, a validation-service that validates the msg and then if it's correct it's passed to it(if not it's redirected to and error queue).

```
@Layer(id="integrationLayer")
public abstract class IntegrationLayer  {
	
	@Compose
	@AnnotatedJmsBroker(persistent=true, connector="tcp://localhost:61618")
	public abstract ActiveMQService getJmsBroker();
	
	@Compose
	@AnnotatedMuleService(propertiesFile="META-INF/invoicing-server.properties")
	public abstract MuleService getMuleService();

}
```


So the invoicing server can be created with composition: a mule service, a validation-service and a invoices-service (the one that actually creates the invoices). In the layer it's only declared the mule-service as the rest of services are handled by mule (but both services are created by jEmbedded and imported by mule):

```
<spring:beans>
   <spring:import resource="classpath:META-INF/invoices-service/invoices-service.xml"/>
   <spring:import resource="classpath:META-INF/validation-service/validation-service.xml"/>
```

The validation needs to be referenced by a custom-inbound router:
```
<custom-inbound-router 						class="org.jsemantic.jembedded.examples.invoicing.mule.MuleValidationService">
    <spring:property name="validationService" ref="validationService"/>
</custom-inbound-router>
```

and the invoices-service will be the core component of the mule model service:

```
<service name="invoicingService">
	<inbound>
	   <jms:inbound-endpoint queue="temp.queue"/>
	</inbound>
	<component>
	   <spring-object bean="invoicesService"/>
	</component>
```