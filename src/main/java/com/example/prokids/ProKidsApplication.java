package com.example.prokids;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.prokids.repositories")
public class ProKidsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProKidsApplication.class, args);
    }

}
