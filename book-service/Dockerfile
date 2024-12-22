FROM ibm-semeru-runtimes:open-17.0.9_9-jdk
EXPOSE 8082
COPY /target/bookEntity-service-0.0.1-SNAPSHOT.jar book-service.jar
ENTRYPOINT ["java", "-jar", "book-service.jar"]