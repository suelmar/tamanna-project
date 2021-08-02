# tamanna-project

## Mandatory tools
* docker
* java 8
* Apache Maven

## Database configuration

Use the following docker command to initial the postgres container:

```docker
docker run --name tamanna-project -e POSTGRES_PASSWORD=test123 -e POSTGRES_DB=project -p 5432:5432 -d postgres
```

For futher details see [postgres docker hub page](https://hub.docker.com/_/postgres)

## Build JAR File

Use the following maven command to build jar file:
```maven
mvn clean install
```

## Run Project

Use the following command to execute the project;

```bash
java -jar interview-project-0.0.1-SNAPSHOT.jar
```

## API Documentation

https://app.swaggerhub.com/apis/suelmar/tamanna-project/1.0.0
