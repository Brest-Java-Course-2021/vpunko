[![Java CI with Maven](https://github.com/Brest-Java-Course-2021/vpunko/actions/workflows/maven.yml/badge.svg?branch=main)](https://github.com/Brest-Java-Course-2021/vpunko/actions/workflows/maven.yml)
# vpunko Hotel

This is simple 'Resident hotel' web application.

## Requirements

* JDK 11
* Apache Maven

## Build application:
```
mvn clean install
```

## Local tests with Jetty Maven Plugin

The [Jetty Maven plugin](https://www.eclipse.org/jetty/documentation/jetty-10/programming-guide/index.html#jetty-maven-plugin) is useful for development and nd local testing.

From the same directory as your root pom.xml, type:
```
mvn jetty:run
```

This starts Jetty and serves up your project on [http://localhost:8080](http://localhost:8080).