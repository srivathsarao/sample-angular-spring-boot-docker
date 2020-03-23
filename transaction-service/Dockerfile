FROM openjdk:8

RUN apt-get update && apt-get install -y maven
COPY . /project
RUN  cd /project && mvn package

#run the spring boot application
ENTRYPOINT ["java","-jar","/project/target/transaction-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
