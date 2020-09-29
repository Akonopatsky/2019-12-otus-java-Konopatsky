package ru.otus.hw17.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

/*
change port and service names via idea example:
    Java vm options:
        -Dserver.port=8080
    parameter arguments:
        --frontend.service.name=frontendService
        --database.service.name=databaseService
        --msServer.port = 8090
        --msServer.address = localhost
*/

@SpringBootApplication
public class FrontMain {
    private static String port;
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(FrontMain.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", port));
        app.run(args);
    }
}
