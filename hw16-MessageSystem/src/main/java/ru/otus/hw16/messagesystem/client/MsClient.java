package ru.otus.hw16.messagesystem.client;

import ru.otus.hw16.messagesystem.message.Message;
import ru.otus.hw16.messagesystem.message.MessageType;

public interface MsClient {

    boolean sendMessage(Message msg);

    void handle(Message msg);

    String getName();

    <T extends ResultDataType> Message produceMessage(String to, T data, MessageType msgType, MessageCallback<T> callback);
}
