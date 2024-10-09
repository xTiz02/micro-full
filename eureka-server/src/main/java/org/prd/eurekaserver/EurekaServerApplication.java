package org.prd.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {
    //Servidor de registro de servicios
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}
