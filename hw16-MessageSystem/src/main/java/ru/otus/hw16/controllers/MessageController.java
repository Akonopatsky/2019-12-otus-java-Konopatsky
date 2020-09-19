package ru.otus.hw16.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.hw16.messageSystemApp.front.FrontendService;
import ru.otus.hw16.controllers.services.UserCreationDto;


@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private final SimpMessagingTemplate template;
    private final FrontendService frontendService;

    public MessageController(SimpMessagingTemplate template, FrontendService frontendService) {
        this.template = template;
        this.frontendService = frontendService;
    }

    @MessageMapping("/newUser")
    public void getMessage(UserCreationDto userCreationDto) {
        logger.info("got user:{}, ", userCreationDto);
        frontendService.saveUser(userCreationDto.createUser(), data -> {
            logger.info("saved user: {}", data.getData());
            getAllUsers();
        });
    }

    @MessageMapping("/user/list")
    public void getAllUsers() {

        logger.info("getAllUsers()");
        frontendService.getAllUsers(userListData -> {
            this.template.convertAndSend("/topic/userList", userListData.getData());
            logger.info("send list of users : {}", userListData.getData());
        });
    }

}
