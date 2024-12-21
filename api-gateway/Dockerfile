FROM ibm-semeru-runtimes:open-17.0.9_9-jdk
EXPOSE 8080
COPY /target/api-gateway-0.0.1-SNAPSHOT.jar gateway-server.jar
ENTRYPOINT ["java", "-jar", "gateway-server.jar"]