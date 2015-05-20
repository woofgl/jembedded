


# jIntegrationTest #

- Integration testing for JEE/Java/POJO applications using embedded servers and the testing framework JUnit 4. There is no need to install any server in order to run them (such as a database, web server etc..).

- Automatic Integration Testing using Maven, Hudson, Cruise Control etc...

- Extend TDD (Test Driven Development) as you can do real integration tests as the same time you are coding. No need to use mocks or any plugin.

- Embedded servers out of the box: (in a service fashion)  WEB (Jetty/Tomcat), Database (Derby/HSQLDB), ESB (Mule) , JMS (ActiveMQ), Web Services Engine(Apache CXF), etc... All are ready to use and relying in defaults for configuration (annotations).

- Assert tests  modules included: Web, Database, IoC, Web Framewoks, ESB, Web Services...
all used in JUnit4 style.

- Different assert test modules can be used in one test unit, for instance EBS and JMS (see the example below).

# Introduction #

jIntegrationTest comes from my own experience working in many projects and trying to figure out how could I do the integration tests easier. Usually you will find two approaches: using mocking frameworks or just deploying the application and testing it.



## The Mocking Approach. ##

One approach is using one of the many **.Mock.** frameworks out there. It's a valid one and it works (easy for POJO's, no that easy if you want to mock something more complex as HTTP request for example) but you probably will found yourself writing a lot of code and spending a lot of time just to replicate the behavior of the application.

Mocking as you are developing and designing it's OK, as you don't have the actual code or HTML's or resources as a database or pages (that's the whole point of mocking).
But at the end of the day, when you have the real code it feels lacking doing the integration testing through all that mocking infrastructure as you are not getting most of the real errors you would get in the real environment (application, system, configuration, environment, dependencies errors etc...).

The good thing about this approach is that fits easily with automatic testing and continuous integration.

## Deploy and Test Approach ##

The other approach and sadly the most common one it's just doing the deployment and see what happens. OK the deployment fails because the XML descriptors are wrong or a file it's missing or we are getting a null pointer. Fix it, compile, test and deploy again. And so on. At last the deployment works, start testing the application, now one SQL sentence is failing. You do it again.. and again :). Now everything seems to work but now one new component is added and the deployment process fails again :(.

Well it's clear how many time it's wasted following this approach and what is worst, you are never sure that you are not breaking something when you add something new having to do expensive regression tests. It's obvious this approach it's no very easy to automatize.

# Why to use Embedded Servers and jIntegrationTest #

At this point it's when the idea of using embedded servers (and of course jIntegrationTest) comes handy as you won't have any of the problems of the former approaches:

  * Don't need to replicate the behavior of the application, just test the expected real results, being a number, a HTTP response, a database invocation, jms message, the creation of a component...
  * Don't need to deploy the application to test it, just run the the integration test units from Eclipse, maven, ant etc.. getting an instant feedback.
  * Don't need to do regression testings as the process can be automatized.


The advantages of using jIntegrationTest are:

  * You get all the above advantages.
  * It works with automatic testing and continuous integration (mvn, hudson etc..)
  * Many embedded servers out of the box: Web (Jetty/Tomcat), DB (HSQLDB), Web Services (Apache CXF), JMS(ActiveMQ), ESB (Mule), IoC (Container and MVC), JMS(ActiveMQ) and RMI.
  * Easy configuration relying on defaults and annotations (almost none).
  * Based on JUnit4 so it's very easy to get into.
  * Many Integration Test Units out of the box: Web, Database, IoC, ESB, Web Services....