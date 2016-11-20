# repohandler
Rest service to handle repository-like objects.

This is a Spring Boot application with maven as a build tool.

To build, you will need Java 8 and maven.
```
mvn clean install -DskipTests
```
If you want to run the test as well, you can just skip the -DskipTests part, or run
```
mvn test
```
separately.

To start the service, go to the generated target directory and execute as any other jar file:
```
java -jar repohandler-1.0.jar
```
This will start up the built in Tomcat server at 127.0.0.1:8080.
