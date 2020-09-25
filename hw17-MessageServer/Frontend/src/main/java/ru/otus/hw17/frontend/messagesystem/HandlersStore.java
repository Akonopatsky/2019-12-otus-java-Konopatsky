package ru.otus.hw17.frontend.messagesystem;

import ru.otus.hw17.frontend.messagesystem.client.ResultDataType;
import ru.otus.hw17.frontend.messagesystem.message.MessageType;

public interface HandlersStore {
    RequestHandler<? extends ResultDataType> getHandlerByType(String messageTypeName);

    void addHandler(MessageType messageType, RequestHandler<? extends ResultDataType> handler);
}
