package ru.otus.hw17.frontend.socket;

import ru.otus.hw17.frontend.messagesystem.message.Message;

public interface MSSocketClient {
    void send(Message msg);
    boolean isReady();
    void start();
    void stop();
}
