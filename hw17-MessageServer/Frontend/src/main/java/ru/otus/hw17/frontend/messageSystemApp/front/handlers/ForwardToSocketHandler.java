package ru.otus.hw17.frontend.messageSystemApp.front.handlers;

import ru.otus.hw17.msserver.dto.UserData;
import ru.otus.hw17.messagesystem.RequestHandler;
import ru.otus.hw17.messagesystem.message.Message;

import java.util.Optional;

public class ForwardToSocketHandler implements RequestHandler<UserData> {
    @Override
    public Optional<Message> handle(Message msg) {
        return Optional.empty();
    }
}
