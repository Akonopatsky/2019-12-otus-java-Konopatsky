package ru.otus.hw16.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.hw16.dataaccsess.core.model.User;
import ru.otus.hw16.messageSystemApp.front.FrontendService;

@Controller
public class WebSocketController {
    private final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    private final SimpMessagingTemplate template;
    private final FrontendService frontendService;

    public WebSocketController(SimpMessagingTemplate template, FrontendService frontendService) {
        this.template = template;
        this.frontendService = frontendService;
    }

    @MessageMapping("/getAllUsers")
    public void getAllUsers() {
        frontendService.getAllUsers(userListData -> {
            this.template.convertAndSend("/user/list", userListData.getData());
            logger.info("send list of users : {}", userListData.getData());
        });
    }

    @MessageMapping("/save")
    public void saveUser(@DestinationVariable User user) {
        frontendService.saveUser(user, data -> logger.info("saved user: {}", data.getData()));
    }

}
