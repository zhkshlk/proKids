package com.example.prokids;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableJpaRepositories("com.example.prokids.repositories")
public class ProKidsApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("POSTGRES_HOST", dotenv.get("POSTGRES_HOST"));
        System.setProperty("JWT_SECRET_ACCESS_EXP", dotenv.get("JWT_SECRET_ACCESS_EXP"));
        System.setProperty("JWT_SECRET_REFRESH_EXP", dotenv.get("JWT_SECRET_REFRESH_EXP"));
        SpringApplication.run(ProKidsApplication.class, args);
    }

}
