package ru.otus.hw16.messagesystem;

import ru.otus.hw16.messagesystem.client.MsClient;
import ru.otus.hw16.messagesystem.message.Message;

public interface MessageSystem {

    void addClient(MsClient msClient);

    void removeClient(String clientId);

    boolean newMessage(Message msg);

    void dispose() throws InterruptedException;

    void dispose(Runnable callback) throws InterruptedException;

    void start();

    int currentQueueSize();
}

