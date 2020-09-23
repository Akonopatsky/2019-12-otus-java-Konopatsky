package ru.otus.hw17.msserver.server;

import ru.otus.hw17.messagesystem.message.Message;

public interface MessageServerClient {
    void send(Message message);
    void handle(Message message);
}
