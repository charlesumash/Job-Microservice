FROM openjdk:17-jdk
COPY target/job-ms.jar .
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "job-ms.jar"]