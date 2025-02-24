FROM openjdk:21-ea-oracle
EXPOSE 8080
ADD target/teacher-service.jar teacher-service.jar
ENTRYPOINT ["java","-jar","/teacher-service.jar"]
