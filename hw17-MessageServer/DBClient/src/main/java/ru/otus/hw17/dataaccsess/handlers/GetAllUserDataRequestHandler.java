package ru.otus.hw17.dataaccsess.handlers;

import ru.otus.hw17.dataaccsess.core.service.DBServiceUser;
import ru.otus.hw17.frontend.messageSystemApp.dto.UserListData;
import ru.otus.hw17.messagesystem.RequestHandler;
import ru.otus.hw17.messagesystem.message.Message;
import ru.otus.hw17.messagesystem.message.MessageBuilder;

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
