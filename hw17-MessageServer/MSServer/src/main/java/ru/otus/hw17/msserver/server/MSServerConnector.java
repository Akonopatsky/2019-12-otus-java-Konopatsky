package ru.otus.hw17.msserver.server;

import ru.otus.hw17.messagesystem.message.Message;

public interface MSServerConnector {
    void start();
    void stop();
    void send(Message message);
    boolean isReady();
}
