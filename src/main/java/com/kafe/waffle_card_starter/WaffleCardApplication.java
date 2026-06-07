package com.kafe.waffle_card_starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.kafe"})
@EntityScan(basePackages = {"com.kafe"})
@EnableJpaRepositories(basePackages ={"com.kafe"} )
@SpringBootApplication(scanBasePackages = "com.kafe")
public class WaffleCardApplication {
    public static void main(String[] args) {
        SpringApplication.run(WaffleCardApplication.class, args);
    }
}