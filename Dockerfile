FROM openjdk:17-jdk
COPY target/jobms-app.jar .
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "jobms-app.jar"]