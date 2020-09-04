package ru.otus.hw16.messagesystem;


import ru.otus.hw16.messagesystem.client.ResultDataType;
import ru.otus.hw16.messagesystem.message.Message;

import java.util.Optional;


public interface RequestHandler<T extends ResultDataType> {
    Optional<Message> handle(Message msg);
}
