package ru.otus.hw17.msserver.server;

import ru.otus.hw17.messagesystem.client.MsClient;
import ru.otus.hw17.messagesystem.message.Message;

public interface MSServer {
    void send(Message msg);
    void setMsClient(MsClient msClient);
    boolean isReady();
    void start();
    void stop();
}
