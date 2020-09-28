package ru.otus.hw17.dbclient.dbhandlers;

import ru.otus.hw17.dataaccsess.core.service.DBServiceUser;
import ru.otus.hw17.messagesystem.RequestHandler;
import ru.otus.hw17.messagesystem.message.Message;
import ru.otus.hw17.messagesystem.message.MessageBuilder;
import ru.otus.hw17.msserver.dto.UserListData;

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