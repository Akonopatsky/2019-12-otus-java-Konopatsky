package ru.otus.hw17.frontend.messagesystem;

import ru.otus.hw17.frontend.messagesystem.client.ResultDataType;
import ru.otus.hw17.frontend.messagesystem.message.Message;

import java.util.Optional;

public interface RequestHandler<T extends ResultDataType> {
    Optional<Message> handle(Message msg);
}
