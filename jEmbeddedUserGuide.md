# Introduction #

jEmbedded is a Service Oriented Container (SOC) for services (embedded or not) or other elements, that promotes re usability and composition through easy configuration (annotations + abstract clases).


What you can do with jEmbedded:

- Business Services Container: create and manage your application business services, managing then in a layer integrated with Spring (aop, tx, hibernate..).

- Implements lighweight SOA using JEE, Java and Ruby: plain services, workflows of services, ESB, Web Services, JMS etc..

- Integration Container: as container for application integration, using an ESB, messaging services etc...

- Rich Applications Container: integrated with GWT/Spring MVC to create RIA applications (SOA oriented).

- Rich Portal Application Provider: introducing the concepts of Gwlets/Widglets to power RIA portals.

-Web enviroment: can be used in any web enviroment, as a holder of a services layer, for example. Have a look at the case study example as it shows the provided integration with SpringMVC.

- Standalone container: implementing services (just one or a layer hierarchy), that you can use in any application (for instance a Swing or a SWT based application).

- Integrated with Spring: you can access Spring bean using the native integration provided by jEmbedded or just creating a`@AnnotatedSpringRepository` as another service within the container. This service wraps a Spring Application Context giving easy access to its beans through the wrapper.

- Easy prototyping: create services faster and test them with the test integration infrastructure included within the container and services.