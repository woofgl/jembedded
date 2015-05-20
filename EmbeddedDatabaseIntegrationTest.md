

# Introduction #

This test allows to test real DAO's, JDBC code etc... using a embedded database. It provides several services to do that: Database Service (produces the embedded databases), a JDBC Service (to execute queries) and a Transactional Service. The generated embedded database could be either HSQLDB (in memory) or Derby (using files).

The simplest usage of this test would be using just the annotation `DatabaseServiceConfiguration` (with default values) and extending the abstract class `AbstractDatabaseIntegrationTest`. Also the methods of the Integration Unit Test need to be marked with the annotation @Test (from JUnit 4).

The execution of the test produces an embedded HSQLDB database (`_jdbc:hsqldb:mem:test_`) plus access to the JDBCService.

To do a database test you can use the general guidelines given in the how to start section:

- Create a POJO class that extends from IntegrationTest.

- Annotate the class with desired annotations (in this case a databaser annotation would be right).

- Create the test methods and annotate them with @Test.

All the annotations relies on default values, except of course the minimum needed parameters.


# DatabaseService Annotation #

  * _provider:_ embedded database provider. By default is `org.jsemantic.services.databaseservice.datasource.hsqldb.HsqldbDataSource`. The alternative provider is `org...DerbyDataSource`.

  * _database:_ database url location. By default is `jdbc:hsqldb:mem:test`.

  * _user:_ database connection user. By default is `sa`.

  * _password:_ database connection password. By default is ``.

  * _databaseCreationScript:_ location of the sql script that creates the test schema.

  * _dataCreationScript:_ location of the sql script that populates the test schema.

  * _doCleanUp:_ intended for the Derby database provider to clean up the database files. By default it's true.

  * _createSchema:_ creates the schema if the scripts are provided. By default it's true.


## Other Related Annotations ##

The `@DatabaseServiceConfiguration` annotation can be used in combination with other Annotations to provide additional services:

`@SpringContext:` provides access to a Spring Application Context.


`@TransactionalTest:` provides a Transaction Context.


# DatabaseService Assert Module #

The assert test modules are static classes that contains the methods provided to do the actual tests. The methods provided depends on the type of module, for instance in this case the assert module is databaseservice.Assert.

As the framework use JUnit4 as a base you can also use the standard assert methods provided with it (assertNull, assertNotNull...).


## Provided Methods ##

  * assertResultNotNull(String query, Object... args)

  * assertResultListNull(String query, Object... args)

  * assertEqualsUpdateResult(String query, int result, Object... args)

  * assertFieldEquals(String query, String fieldName, Object expected, Object... args)

  * List<Map<String, Object>> executeQueryForList(String query,
> > Object... args)Map<String, Object> executeQuery(String query, Object... args)

  * int executeUpdate(String query, Object... args)

  * assertResultNull(String query, Object... args)

  * assertResultListNotNull(String query, Object... args)


# Examples #


## Example 1: Simple Test ##

```
//
import static org.jsemantic.jintegration.test.database.Assert.*;

@DatabaseServiceConfiguration
public class AnnotatedSimpleDatabaseIntegrationTest extends IntegrationTest {
	
	@Test
	public void testInfrastructure() {
		assertNotNull(getDatabaseService());
		assertNotNull(getJdbcService());
	}

     	@Test
	public void test() throws Exception {
		createTable();
		assertEqualsUpdateResult(
		"insert into test values(3, 'test3', 'Test3 description')",
						1);
		
		assertResultNotNull("select * from test where id = 3");
	}
	
	private void createTable() {
		getJdbcService().executeUpdate("CREATE TABLE test" + "(id INTEGER IDENTITY, "+ "	name VARCHAR(25), " + "	description VARCHAR(56))");
	}

```

In the example above the table is created using the Jdbc Service provided. This is not need to be always the case as can be seen in the next example.


## Example 2: Using scripts to create the embedded database ##

In this example the database and the data are created using sql scripts.

```

import static org.jsemantic.jintegration.test.database.Assert.*;

@DatabaseServiceConfiguration(database = "jdbc:hsqldb:mem:test", 

databaseCreationScript = "META-INF/integration-database-test/scripts/database-hsqldb.sql", 

dataCreationScript = "META-INF/integration-database-test/scripts/data.sql")

public class AnnotatedDatabaseIntegrationTest extends IntegrationTest {
	
	@Test
	public void testInfrastructure() {
		assertNotNull(getDatabaseService());
		assertNotNull(getJdbcService());
	}
	
	@Test
	public void testSelect() throws Exception {
		assertEqualsUpdateResult(
		"insert into test values(4, 'test4', 'test description 4')",
				1);
		assertResultNotNull("select * from test where id=4");
	}
	
	@Test
	public void equalsTest() throws Exception {
		assertFieldEquals("select * from test where id=1", "name", "test1");
	}

}

```


## Example 3: Using a Spring Container and a Derby Embedded Database ##

If you need to test DAO'S created by a Spring Context you can use the Annotation @SpringContext providing the location of the context file.

Once the test has started the Spring Container can be accesed using the method `getContainer()` from the `AbstractDatabaseIntegrationTest` class.

In this example a Embedded Derby database is used. In order to do that the provider parameter must be provided. The usage of the test is just the same af if the database used was HSQLDB.


```

import static org.jsemantic.jintegration.test.database.Assert.*;

@SpringContext(configurationFile = "classpath:META-INF/derby-application-context.xml")

@DatabaseServiceConfiguration(database = "test", 
		databaseCreationScript = "database.sql", 
		dataCreationScript = "data.sql", 
provider="org.jsemantic.services.databaseservice.datasource.derby.DerbyDataSource")
		
public class AnnotatedDerbyDatabaseSpringIntegrationTest extends
		AbstractDatabaseIntegrationTest {

	private TestDAO dao = null;

	@Override
	protected void init() throws Exception {
		super.init();
		dao = (TestDAO) getContainer().getBean("testDAO");
	}
	
	@Test
	public void testInfrastructure() {
		assertNotNull(getContainer());
		assertNotNull(getDatabaseService());
		assertNotNull(getJdbcService());
	}
	
	@Test
	public void test() throws Exception {
		dao.executeUpdate("insert into test values(?,?, ?)", new Object[] { 4,
				"Test 4", "Test Description Number 4" });

		getJdbcService().executeUpdate(
				"insert into test values(?,?, ?)",
				new Object[] { 5, "Test 5", "Test Description Number 5" });

		getJdbcService().executeQueryForList(
				"select * from test");

		assertResultNotNull("select * from test where id=4");
	}
	
	@Test
	public void test2() throws Exception {
		dao.executeUpdate("insert into test values(?,?, ?)", new Object[] { 4,
				"Test 4", "Test Description Number 4" });

		assertResultNotNull("select * from test where id=4");
	}
}

```


## Example 4: Using a Transactional Context ##

The `@TransactionTest` Annotation creates a transparent Transactional Context through the methods of the test.

```

import static org.jsemantic.jintegration.test.database.Assert.*;

@TransactionalTest

@DatabaseServiceConfiguration(database= "jdbc:hsqldb:mem:test")

@SpringContext(configurationFile = "classpath:META-INF/application-context.xml")

public class TransactionalAnnotatedDatabaseSpringIntegrationTest extends IntegrationTest {

	private TestDAO dao = null;
	
	@Override
	protected void init() throws Exception {
		super.init();
		dao = (TestDAO)getApplicationContext().getBean("testDAO");
		createTable();
	}
	
        @Test
	public void test() throws Exception {
		dao.executeUpdate("insert into test values(?,?, ?)", new Object[] { 4,
				"Test 4", "Test Description Number 4" });

		getJdbcService().executeUpdate(
				"insert into test values(?,?, ?)",
				new Object[] { 5, "Test 5", "Test Description Number 5"});

		getJdbcService().executeQueryForList(
				"select * from test");

		assertResultNotNull("select * from test where id=4");
	}
	
	@Test
	public void test2() throws Exception {
		dao.executeUpdate("insert into test values(?,?, ?)", new Object[] { 4,
				"Test 4", "Test Description Number 4" });

		assertResultNotNull("select * from test where id=4");
	}
}

```