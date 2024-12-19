FROM ibm-semeru-runtimes:open-17.0.9_9-jdk
#Expone el puerto 8888 para que se pueda acceder al servicio desde el exterior como http://localhost:8888
EXPOSE 8888
COPY /target/config-service-0.0.1-SNAPSHOT.jar config-service.jar
ENTRYPOINT ["java", "-jar", "config-service.jar"]