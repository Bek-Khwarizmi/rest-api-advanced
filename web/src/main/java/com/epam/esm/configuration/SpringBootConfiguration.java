package com.epam.esm.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaAuditing
@EnableJpaRepositories("com.epam.esm.repository")
@EntityScan(basePackages = {"com.epam.esm"})
@ComponentScan(basePackages = {"com.epam.esm"})
@SpringBootApplication
public class SpringBootConfiguration {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootConfiguration.class, args);
    }
}
