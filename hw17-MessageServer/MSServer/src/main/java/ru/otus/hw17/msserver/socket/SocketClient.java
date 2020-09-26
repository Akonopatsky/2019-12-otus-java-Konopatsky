package ru.otus.hw17.msserver.socket;

import ru.otus.hw17.messagesystem.client.MsClient;
import ru.otus.hw17.messagesystem.message.Message;

public interface SocketClient {
    void send(Message msg);
    void setMsClient(MsClient msClient);
    boolean isReady();
    void start();
    void stop();
}
