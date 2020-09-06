package ru.otus.hw16.messageSystemApp.db.handlers;

import ru.otus.hw16.dataaccsess.core.model.User;
import ru.otus.hw16.dataaccsess.core.service.DBServiceUser;
import ru.otus.hw16.messageSystemApp.dto.UserData;
import ru.otus.hw16.messagesystem.RequestHandler;
import ru.otus.hw16.messagesystem.message.Message;
import ru.otus.hw16.messagesystem.message.MessageBuilder;
import ru.otus.hw16.messagesystem.message.MessageHelper;

import java.util.Optional;

public class SaveUserDataRequestHandler implements RequestHandler<UserData> {

    private final DBServiceUser dbService;

    public SaveUserDataRequestHandler(DBServiceUser dbService) {
        this.dbService = dbService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        User user = ((UserData) MessageHelper.getPayload(msg)).getData();
        synchronized (dbService) {
            dbService.saveUser(user);
        }
        UserData resultData = new UserData(user);
        return Optional.of(MessageBuilder.buildReplyMessage(msg, resultData));
    }
}
