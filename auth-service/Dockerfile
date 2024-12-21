FROM ibm-semeru-runtimes:open-17.0.9_9-jdk
EXPOSE 8081
COPY /target/auth-service-0.0.1-SNAPSHOT.jar auth-server.jar
ENTRYPOINT ["java", "-jar", "auth-server.jar"]