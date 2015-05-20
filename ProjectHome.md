# jEmbedded #

- SOC Container (Service Oriented Container) focused on managing and composing Services and other elements (as pojos, components etc...).

- Create and test faster your Integration/SOA/RIA applications (or prototypes) creating  services using annotations and composition of other services as Mule, Jetty, jBPM, Hibernate, Rules,...).

- Implement lightweight SOA using jee/java and ruby.

- Create proxy dynamic services using just an abstract class + annotations + composition of other services.

- Use the core annotations to create and configure your services or extend them to create new ones.

- Create work flows of services in a graphical way using the native jBPM support.

- Create your dynamic business services using service composition (+ Spring, AOP, TX, Hibernate etc).

- Integrated with GWT/Spring MVC to create SOC-RIA applications.

- Introducing the Gwlets/Widglets components, similar in concept to portlets/applets but using GWT widgets and embedded services.

- Have a look at the services examples and the getting started guide to have an idea of what you can do (also at the lightweight SOA case study for an advanced example).


## Features ##

  * Proxy Abstract Services and dynamic composition: create services using abstract classes and annotations without providing any implementation.
  * Annotation inheritance, create your customs annotations from the corea annotations.
  * Compose your service workflows graphically using the jBPM native support.
  * Implement services using Java or Ruby.
  * 100% Annotation based configuration (plus .properties files for externalization).
  * Can be used as a standalone container, in a web environment or integrated with other containers.
  * Spring native support (Spring/Spring MVC).
  * Testing support integrated within the framework using static Assert classes.
  * Monitor and manage the services through JMX (status, start, stop...).
  * Spring native support (Spring/Spring MVC).
  * Maven plugin.
  * Several embedded services are provided out of the box and ready to use:
> - Embedded-database (HSQLDB/ Derby).

> - Mule-Service and client (native support + validation-service).

> - jBPM-Service (native support).

> - JMSBroker-Service and client (ActiveMQ).

> - CXFServer-Service (Apache CXF webservices).

> - GWT-Service (Google Web Toolkit integration + SpringMVC).

> - GWlet-Service (introducing the gwlets and widglets components).

> - Spring-Service (native support + supporting services: i18n, validation,   hibernate/jpa, aop, orm and mvc).

> - Jetty-Service and Http-Client.

> - Jdbc-Service (HSQLDB / Derby).

> - Tomcat-Service and Http-Client.

> - Jasper-Service (compiler).

> - jRuby-Service.

> - Validation-Service (annotation based, oval).

> - Dao-Service (ORM).

> - Hibernate-Service.

> - Agent-Service.

> - JMX-Service.

> - Rules-Service (Drools).

> - Properties Service.
