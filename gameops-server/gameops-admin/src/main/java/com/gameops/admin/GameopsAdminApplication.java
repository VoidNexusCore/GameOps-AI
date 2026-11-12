package com.gameops.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.gameops")
public class GameopsAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(GameopsAdminApplication.class, args);
    }
}
