package org.prd.authservice;

import org.prd.authservice.util.RouteValidator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableConfigurationProperties(RouteValidator.class)
@EnableDiscoveryClient
@SpringBootApplication
public class AuthServiceApplication implements CommandLineRunner {

   /* @Autowired
    private RoleRepository roleRepository;*/

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        /*roleRepository.save(new Role(
                null,
                RoleEnum.ROLE_USER,
                "User role"
        ));
        roleRepository.save(new Role(
                null,
                RoleEnum.ROLE_ADMIN,
                "Admin role"
        ));*/
    }
}