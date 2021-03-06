package ru.otus.hw16.messageSystemApp.db.handlers;

import ru.otus.hw16.dataaccsess.core.service.DBServiceUser;
import ru.otus.hw16.messageSystemApp.dto.UserData;
import ru.otus.hw16.messageSystemApp.dto.UserListData;
import ru.otus.hw16.messagesystem.RequestHandler;
import ru.otus.hw16.messagesystem.message.Message;
import ru.otus.hw16.messagesystem.message.MessageBuilder;


import java.util.Optional;


public class GetAllUserDataRequestHandler implements RequestHandler<UserListData> {
    private final DBServiceUser dbService;

    public GetAllUserDataRequestHandler(DBServiceUser dbService) {
        this.dbService = dbService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        UserListData resultData;
        resultData = new UserListData(dbService.getAllUsers());
        return Optional.of(MessageBuilder.buildReplyMessage(msg, resultData));

    }
}
