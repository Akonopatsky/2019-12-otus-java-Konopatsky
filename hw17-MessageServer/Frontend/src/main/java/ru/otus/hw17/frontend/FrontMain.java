package ru.otus.hw17.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class FrontMain {
    private static String port;
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(FrontMain.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", port));
        app.run(args);
    }
}
