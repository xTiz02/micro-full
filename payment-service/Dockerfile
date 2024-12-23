FROM ibm-semeru-runtimes:open-17.0.9_9-jdk
EXPOSE 8085
COPY /target/payment-service-0.0.1-SNAPSHOT.jar payment-server.jar
ENTRYPOINT ["java", "-jar", "payment-server.jar"]