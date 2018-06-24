# timeseries-repo
Store timeseries data in Apache Cassandra.

## Setup
Start Cassandra via the official docker [Image](https://hub.docker.com/_/cassandra/) and expose port 9042:
```
docker run -p9042:9042 --name cassandra -d cassandra:latest
```

Build the application with Maven and run the jar file
```
java -jar target/timeseries-repo-0.0.1-SNAPSHOT.jar
```

## Read and write timeseries data

Read by name and version:  
http://localhost:8080/timeseries/t1/v1

Read by Name, Version and Interval:   
http://localhost:8080/timeseries/t1/v1?from=2018-06-23T09:25:01.464Z&to=2018-06-24T09:25:01.464Z