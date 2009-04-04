/* Adolfo Estevez Jimenez: aestevezjimenez@gmail.com
 * Copyright 2007-2009
 * http://code.google.com/p/jcontenedor,
 * http://code.google.com/p/jembedded,
 * http://code.google.com/p/jservicerules
 * http://semanticj2ee.blogspot.com/ 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jsemantic.jintegration.test.jms.skeletal;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import org.jsemantic.jintegration.test.core.skeletal.AbstractIntegrationTest;
import org.jsemantic.services.amqservice.ActiveMQService;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public abstract class AbstractJMSIntegrationTest extends
		AbstractIntegrationTest {

	private String user = ActiveMQConnection.DEFAULT_USER;
	private String password = ActiveMQConnection.DEFAULT_PASSWORD;
	private int messageSize = 255;
	private JmsTemplate jmsTemplate = null;

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public abstract void test() throws Exception;

	protected void sendMessage(String destination, final String message) {
		this.jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}

	private ActiveMQService getJMSService() {
		return (ActiveMQService) super
				.getService("jmsService");
	}

	private String createMessageText(int index) {
		StringBuffer buffer = new StringBuffer(messageSize);
		buffer.append("Message: " + index + " sent at: " + new Date());
		if (buffer.length() > messageSize) {
			return buffer.substring(0, messageSize);
		}
		for (int i = buffer.length(); i < messageSize; i++) {
			buffer.append(' ');
		}
		return buffer.toString();
	}

	public void testDestination(String destination) {
		sendMessage(destination, "test");
	}

	public void testDestinationMessage(String destination, String message) {
		sendMessage(destination, message);
	}

	public void testDestinationCreation(String subject, String url) {

		Destination destination = null;

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				user, password, url);
		Connection connection = null;
		try {
			connection = connectionFactory.createConnection();

			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			destination = session.createQueue(subject);

			// Create the producer.
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			TextMessage message = session
					.createTextMessage(createMessageText(0));

			producer.send(message);
			/**
			assertTrue(getJMSService().getBroker().getDestinationMap()
					.containsKey("queue://" + subject));
			 **/
		} catch (Throwable e) {
			fail(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (Throwable ignore) {
			}
		}

	}

}
