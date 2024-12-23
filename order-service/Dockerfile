FROM ibm-semeru-runtimes:open-17.0.9_9-jdk
EXPOSE 8084
COPY /target/order-service-0.0.1-SNAPSHOT.jar order-server.jar
ENTRYPOINT ["java", "-jar", "order-server.jar"]