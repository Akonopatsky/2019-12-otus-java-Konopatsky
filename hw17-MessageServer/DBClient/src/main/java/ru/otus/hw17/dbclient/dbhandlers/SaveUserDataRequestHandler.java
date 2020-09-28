package ru.otus.hw17.dbclient.dbhandlers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.dataaccsess.core.service.DBServiceUser;
import ru.otus.hw17.messagesystem.RequestHandler;
import ru.otus.hw17.messagesystem.message.Message;
import ru.otus.hw17.messagesystem.message.MessageBuilder;
import ru.otus.hw17.messagesystem.message.MessageHelper;
import ru.otus.hw17.msserver.dto.UserData;
import ru.otus.hw17.msserver.model.User;

import java.io.*;
import java.util.Optional;

public class SaveUserDataRequestHandler implements RequestHandler<UserData> {
    private static final Logger logger = LoggerFactory.getLogger(SaveUserDataRequestHandler.class);

    private final DBServiceUser dbService;

    public SaveUserDataRequestHandler(DBServiceUser dbService) {
        this.dbService = dbService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        User user = ((UserData) MessageHelper.getPayload(msg)).getData();
        dbService.saveUser(user);
        UserData resultData = new UserData(user);

        Message msg1 = MessageBuilder.buildReplyMessage(msg, resultData);

        try (FileOutputStream fout = new FileOutputStream("f111.txt")) {
            fout.write(msg1.getPayload());

        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.error("des {}", MessageHelper.getPayload(msg1).toString());
        return Optional.of(msg1);
        //  return Optional.of(MessageBuilder.buildReplyMessage(msg, resultData));
    }
}
