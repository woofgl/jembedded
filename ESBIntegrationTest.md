# Introduction #

To test a ESB based  application you can use the general guidelines given in the how to start section:

- Create a POJO class that extends from `IntegrationTest`.

- Annotate the class with desired annotations (in this case a ESB annotation would be right).

- Create the test methods and annotate them with @Test.

All the annotations relies on default values, except of course the minimum needed parameters.


# ESB Annotations #

@MuleServiceConfiguration(configurationFile)

  * configurationFile: location of the mule service configuration file.


# ESB Assert Module #

The assert test modules are static classes that contains the methods provided to do the actual tests. The methods provided depends on the type of module, for instance in this case the assert module is mule.Assert.

As the framework use JUnit4 as a base you can also use the standard assert methods provided with it (assertNull, assertNotNull...).


## Provided Methods ##

  * assertSendMessage(String uri, String message)

  * assertReply(String uri, String message)

  * assertEqualsReplyMessage(String uri, String message)

  * assertMuleServiceNotNull(String serviceId)

  * assertMuleServiceNull(String serviceId)

  * assertMuleServiceInboundRouterNotNull(String serviceId)

  * assertMuleServiceInboundRouterNull(String serviceId)

  * assertMuleServiceOutboundRouterNotNull(String serviceId)

  * assertMuleServiceOutboundRouterNull(String serviceId)

  * assertMuleServiceInboundEndPointNotNull(String serviceId, String endpointName)

  * assertMuleServiceInboundEndPointNull(String serviceId, String endpointName)


# Examples #

In this example this MULE Model is being tested:

```
      <jms:activemq-connector name="jmsConnection"
		brokerURL="tcp://localhost:61616" />
   
    	      <model name="JMSModel">
	         <service name="queue-to-queue">
			<inbound>
			   <jms:inbound-endpoint queue="in.queue"/>
			</inbound>
                          <echo-component/>
			<outbound>
                	   <pass-through-router>
                    	      <jms:outbound-endpoint queue="out.queue"/>
               	 	   </pass-through-router>
           		</outbound>
		 </service>
	       </model>
```

A message arrives to the `in.queue`, pass through the `echo-component` and arrives to the `out.queue`.

```

//Assert Module for JMS test.
import static org.jsemantic.jintegration.test.jms.Assert.*;

//Assert Module for ESB test.
import static org.jsemantic.jintegration.test.mule.Assert.*;

//ActiveMQ Broker configuration. Default Values used
@ActiveMQServiceConfiguration
@ConnectionFactoryConfiguration
//MuleService including the model xml file.

@MuleServiceConfiguration(configurationFile = "META-INF/integration-mule-test/jms-config.xml")

public class EmbeddedJMSMuleIntegrationTest extends IntegrationTest {
	
        private JMSClient jmsClient = null;
	
	@Test
	public void checkESBInOutMessage() throws Exception {
                // Message sent to ESB Inbound JMS Endpoint
		assertSendMessage("jms://in.queue", "test");

		Message message = getJMSClient().getJmsTemplate().receive("out.queue");
		assertNotNull(message);
		// Message retrieved from ESB Outbound JMS EndPoint
		assertEquals(((TextMessage) message).getText(), "test");
	
	}

        @Test
	public void testInfrastructure() {
		assertMuleServiceNotNull("queue-to-queue");
		assertMuleServiceInboundRouterNotNull("queue-to-queue");
		assertMuleServiceOutboundRouterNotNull("queue-to-queue");
		assertMuleServiceInboundEndPointNotNull("queue-to-queue",
				"endpoint.jms.in.queue");
	}

}

```