package ru.otus.hw17.messagesystem;

import ru.otus.hw17.messagesystem.client.ResultDataType;
import ru.otus.hw17.messagesystem.message.MessageType;

public interface HandlersStore {
    RequestHandler<? extends ResultDataType> getHandlerByType(String messageTypeName);

    void addHandler(MessageType messageType, RequestHandler<? extends ResultDataType> handler);
}
