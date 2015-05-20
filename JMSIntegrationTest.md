

# Introduction #

To test a JMS messaging application you can use the general guidelines given in the how to start section:

- Create a POJO class that extends from IntegrationTest.

- Annotate the class with desired annotations (in this case a JMS broker annotation would be right).

- Create the test methods and annotate them with @Test.

All the annotations relies on default values, except of course the minimum needed parameters.

# JMS Broker Annotations #

  * ActiveMQService : @ActiveMQServiceConfiguration()

  * ConnectionFactory: @ConnectionFactoryConfiguration()


## ActiveMQService Annotation ##

## ConnectionFactory Annotation ##


## Other related annotations ##

  * QueueDestination: @QueueDestinationConfiguration()
  * TopicDestination: @TopicDestinationConfiguration()


# JMS Assert Module #

The assert test modules are static classes that contains the methods provided to do the actual tests. The methods provided depends on the type of module, for instance in this case the assert module is jms.Assert.

As the framework use JUnit4 as a base you can also use the standard assert methods provided with it (assertNull, assertNotNull...).

## Provided Methods ##

  * sendMessage(String destination, final String message)

  * assertQueueDestinationCreation(String subject)

  * assertTopicDestinationCreation(String subject)

  * assertTemporaryQueueDestinationCreation()

  * assertTemporaryTopicDestinationCreation()

  * assertReceiveMessage(String destination)

  * assertReceiveMessage(String destination, long timeout)

  * assertReceiveMessage(Destination destination)

  * assertReceiveMessage(Destination destination, long timeout)


# Examples #

Testing JMS Destinations (ActiveMQ Embedded broker).

```

//Assert JMS Test Module.
import static org.jsemantic.jintegration.test.jms.Assert.*;

@ActiveMQServiceConfiguration
@ConnectionFactoryConfiguration

@QueueDestinationConfiguration(subject = "TEST.QueueA")
@TopicDestinationConfiguration(subject = "TEST.TopicA")

public class EmbeddedJMSIntegrationTest extends IntegrationTest {

	@Test
	public void testQueueDestinationCreation() {
		assertQueueDestinationCreation("TEST.TEST");
	}

	@Test
	public void testAnnotatedQueuDestinationCreation() {
		assertNotNull(getDestination(QueueDestinationConfiguration.class));
		assertReceiveMessage("TEST.QueueA");
		assertReceiveMessage(getDestination(QueueDestinationConfiguration.class));
	}

	@Test
	public void testQueueReceive() {
		getJMSClient().sendMessage("TEST.QueueA", "testing msg");
		Message message = getJMSClient().getJmsTemplate()
				.receive("TEST.QueueA");
		assertNotNull(message);
	}

	@Test
	public void testAnnotatedTopicDestinationCreation() {
		assertNotNull(getDestination(TopicDestinationConfiguration.class));
		assertReceiveMessage("TEST.TopicA");
	}

	@Test
	public void testQueueError() {
		getJMSClient().sendMessage("TEST.QueueB", "testing msg");
		Message message = getJMSClient().getJmsTemplate()
				.receive("TEST.QueueA");
		assertNull(message);
	}

}

```