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

## Start application
###Start Rest application

To start Rest server:

```
java -jar ./rest-app/target/rest-app-1.0-SNAPSHOT.jar
```

Server up on [http://localhost:8090/apartments](http://localhost:8090/apartments).

###Start Web application
To start Web application:

```
java -jar ./web-app/target/web-app-1.0-SNAPSHOT.jar
```
Server up on [http://localhost:8080](http://localhost:8080).
