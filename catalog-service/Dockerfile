FROM ibm-semeru-runtimes:open-17.0.9_9-jdk
EXPOSE 8083
COPY /target/catalog-service-0.0.1-SNAPSHOT.jar catalog-service.jar
ENTRYPOINT ["java", "-jar", "catalog-service.jar"]