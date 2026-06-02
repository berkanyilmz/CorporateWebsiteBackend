package com.example.adminpanelbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.example"})
@EntityScan(basePackages = {"com.example"})
@EnableJpaRepositories(basePackages = {"com.example"})
@SpringBootApplication
public class AdminPanelBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminPanelBackendApplication.class, args);
    }

}
