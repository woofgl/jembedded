# Introduction #

## Framework setup ##

- You will need to have MAVEN 2.x installed in your system.

- Install manually the jars that you will find in the lib directory (jbpm and xi8i plugin)
> as they are quite elusive to be downloaded from online repositories.

- Go to the directory where you have unzipped jembedded and type “mvn clean install.” (for a quicker installation you can avoid the test execution -Dmaven.test.skip=true)

- It will download the needed dependencies from various sites, compiling and installing the modules and examples.

- If you want to generate the eclipse projects type: mvn eclipse:eclipse.

- In order to user the core container you will need to declare the following dependency in your pom:

```
		<dependency>
			<groupId>org.jsemantic.jembedded</groupId>
			<artifactId>jembedded-core</artifactId>
			<version>0.1.3</version>
		</dependency>
```



