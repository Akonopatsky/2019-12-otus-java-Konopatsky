package ru.otus.hw16.messageSystemApp.db.handlers;

import ru.otus.hw16.messageSystemApp.db.DBService;
import ru.otus.hw16.messageSystemApp.dto.UserData;
import ru.otus.hw16.messagesystem.RequestHandler;
import ru.otus.hw16.messagesystem.message.Message;
import ru.otus.hw16.messagesystem.message.MessageBuilder;
import ru.otus.hw16.messagesystem.message.MessageHelper;

import java.util.Optional;


public class GetUserDataRequestHandler implements RequestHandler<UserData> {
    private final DBService dbService;

    public GetUserDataRequestHandler(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        UserData userData = MessageHelper.getPayload(msg);
        UserData data = new UserData(userData.getUserId(), dbService.getUserData(userData.getUserId()));
        return Optional.of(MessageBuilder.buildReplyMessage(msg, data));
    }
}
