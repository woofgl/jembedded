# Introduction #

Included with the `jEmbeddded` distribution there is maven based pluging that allows to execute the container from the console right away. You don't need to setup any classpaths or libs the plugin takes care of it.


## Plugin setup ##

The plugin setup it's quite easy, just add the plugin definition to your project's pom:

```
<plugins>
		<plugin>
		<groupId>org.jsemantic.jembedded</groupId>
		<artifactId>jembedded-plugin</artifactId>
		<version>0.2-SNAPSHOT</version>
		<configuration>
			<argLine>-Xms256m -Xmx512m</argLine>

				<additionalClasspathElements>
					<additionalClasspathElement>${basedir}/target/classes</additionalClasspathElement>
				</additionalClasspathElements>	
				<useSystemClassLoader>true</useSystemClassLoader>
					<container>
						<id>test</id>
						<classes>
						<class></class>
						</classes>
					</container>
				</configuration>
		</plugin>
	</plugins>
```

In order to setup the plugin only is needed to add the classes you want to the container handles for you, services etc.. buy it's a good idea to have an entry point for a example, a facade of services.
```
<container>
	<id>test</id>
		<classes>
		    <class>invoicing.server.InvoicingServerEntryPoint</class>
		</classes>
</container>
```

## Plugin tasks ##

There is one task yet:

`jembedded:start`: it will start the container.

From maven: `mvn -o jembedded:start`