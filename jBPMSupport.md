# Introduction #

jBPM it's the leading Open Source BPM from jBoss. I've been using it for years so I think I can recommend it.

You should avoid using it for everything though (as you have to pay the price of resources) but  you can use it when you have a business use case that it's likely to change over the time or it needs to invoke different systems, resources, services etc... (in other words a workflow) in order to implement the functionality.

That kind of functionality can be provided through service composition than can be achieved in a programatic way (or if you use jEmbedded using annotation/services) or using a BPM engine. A BPM gives you the flexibility of composing services in a graphical way (using a Eclipse plugin, GPDL) that generates a readable xml. This way you can create/change without having to glue the services together and without having to code (well not that much :))

Basically you need to design a state diagram and the transitions between them (fired by events). You will need some basic knowledge of how jBPM works, so I recommend you the official tutorial for that. You don't need to know all the gory details but at least how to compose a jPDL diagram as most of the technical details are wrapped up by the service.


## jEmbedded jBPM Support ##

I have provided native support for jBPM that basically means you can reference the container services directly from the jBPM context (the jPDL file).

The support is provided by a new service: jbpm-service.


```
	        <dependency>
			<groupId>org.jsemantic.services</groupId>
			<artifactId>jbpm-service</artifactId>
			<version>0.2.0-SNAPSHOT</version>
		</dependency>
```

The service can be used as any other service of the framework or using the following annotations:

`@BPMProcess`
| Attribute | Description |
|:----------|:------------|
| inherits  | `AnnotatedService.class`|

This is a helper annotation that enables to create BPM processes in an easier way.
As this annotation inherits from `@AbstractService` annotation you can override any of its attributes (id, isSingleton, resources etc...)

`@ImplementedByBPM`
| Attribute | Description |
|:----------|:------------|
| bpmServiceRef | Service name of the bpm service, by default "jbpmService" |
| processKey | The key name of the process |

Using this annotation you can provide the implementation of one method using a BPM process, just passing the process key and the input parameters.


For example:

```
@ImplementedByBPM(processKey="Order")
public Object executeOrder();
```

This method will invoke the process "Order" with no parameters.

In order to this annotation to work the process file must follow a template (this is not true if you are using the service directly):

```
<?xml version="1.0" encoding="UTF-8"?>

<process name="Order" xmlns="http://jbpm.org/4.0/jpdl">
  //Start state, you always needs this one.
  <start g="16,80,48,48">
    <transition to="invoke"/> // it will go to the state "invoke"
  </start>
   
 // After the the start state you can invoke as many services you want, having in mind that the 
//initial params are stored: service_param_0, service_param_1,..., service_param_n
//cmtService is a service retrieved from the jEmbedded Container.
  
   //The java expresion represents an state
  <java expr="#{cmtService}" g="141,216,80,40" method="service" name="invoke" var="result_service_1">
   <transition g="-52,-18" name="to_invoke" to="invoke2"/>
  </java>
   
  <java expr="#{cmtService}" g="312,311,99,52" method="service" name="invoke2" var="result_service_end">
    <arg><object expr="#{result_service_1}"/></arg>
    <transition to="wait"/>
  </java>
  

  // you must add this state always before the end state,
  // this due how the service works.
  <state g="489,216,88,52" name="wait">
   <transition to="end"/>
  </state>
  
  <end g="642,201,48,48" name="end"/>

</process>

```

And the template could be something like this:

```
<process name="process.key" xmlns="http://jbpm.org/4.0/jpdl">
  //Order is the jey name of the process.
  
  <start g="16,19,48,48">
    <transition to="invoke_service_1" />
  </start>

  <java name="invoke" expr="#{service_1}" method="service_1_method" var="result_service_1">
        <arg><object expr="#{service_param_0}"/></arg>
        <arg><object expr="#{service_param_1}"/></arg>
        <arg><object expr="#{service_param_n}"/></arg>
   	<transition to="invoke_service_2 />
  </java>
  
  <java name="invoke_service_2" expr="#{service_2}" method="service_2_method" var="result_service_2">
                <arg><object expr="#{result_service_1}"/></arg>
   		<transition to="invoke_service_n />
  </java>
  
   <java name="invoke_service_n" expr="#{service_n}" method="service_n_method" var="result_service_end">
                <arg><object expr="#{result_service_n-1}"/></arg>
   		<transition to="wait />
  </java>


 <state name="wait" g="352,17,88,52">
   <transition to="end" />
  </state>
  
  <end name="end" g="269,20,48,48"/>

</process>
```

This annotation can be used (of course!) with `@BPMProcess`:


```
@BPMProcess(id="orderProcess", resources={CMTServiceImpl.class})
public interface OrderProcess {
	
	@Compose(ref="jbpmService")
	@JBPMService(processFile="META-INF/jbpm-service/Order.jpdl.xml")
	public org.jsemantic.services.jbpm.service.JBPMService getBPMService();
	
        @ImplementedByBPM(processKey="Order")
	public String executeOrder();

	@ImplementedBy(ref="jbpmService", refMethodName="startProcessByKey")
	public ProcessInstance executeProcess(String processKey);
}
```

The first method invokes directly the jBPMService whereas the second implements a BPM process.