## Overview

The project implements the logic to move o hovver inside a room and to clean dirt patches. 

## Prerequisites
- JDK 1.8 (Oracle)
- Maven 3

## Installation 

```
mvn clean install
```

## Deployment
```
mvn spring-boot:run
```

## Url to interact with Swagger
```
http://localhost:8080/swagger-ui.html
```

## The format of the input should be:

```json
{
  "roomSize" : [5, 5],
  "coords" : [1, 2],
  "patches" : [
    [1, 0],
    [2, 2],
    [2, 3]
  ],
  "instructions" : "NNESEESWNWW"
}
```

## The format of the output should be:

```json
{
  "coords" : [1, 3],
  "patches" : 1
}
```
