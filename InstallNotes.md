

# How to Install the Framework #

I've used Maven 2.0.x to handle the dependencies and the life cycle of the project so the easiest way to use the framework it's installing it in a maven repository . So I recommend that you'd get Maven 2.0.9 if you don't already have it.

If you don't want to use Maven you can find the framework jars it the dist directory of the distribution zip file, but you will have to get the dependencies yourself and than can be a lot of work.


## Steps to install the framework ##

- Download the latest snapshot version from the downloads page.

- Unzip the file to the directory of your choice.

- Copy the contains of lib-mvn\repository folder into your own maven repository (usually .m2). It contains the dependencies that you can't download from other sites (jiservice, services...).

- Go to the src directory and type `mvn clean install`.

- It will download the needed dependencies, compile, build, test, package and install the framework in your maven repository.



# How to use the framework in your projects #

After having the framework installed you can reference the dependency in your project's pom:

```
		<dependency>
			<groupId>org.jsemantic.jintegration.test</groupId>
			<artifactId>jintegration-test-core</artifactId>
			<version>0.9.3-SNAPSHOT</version>
		</dependency>
```

The core includes all the test modules except the JSF one. In future versions I will split the framework in different modules (and one in all version). In the meantime you can use the mvn exclusions if you don't want to import all the dependencies.

The jsf-integration-test module has it own dependency:

```
		<dependency>
			<groupId>org.jsemantic.jintegration.test</groupId>
	                <artifactId>integration-jsf-test</artifactId>
	                <version>integration-jsf-test-0.2.0-SNAPSHOT</version>
		</dependency>
```


- If you want to generate the eclipse projects just type: `mvn eclipse:eclipse`.