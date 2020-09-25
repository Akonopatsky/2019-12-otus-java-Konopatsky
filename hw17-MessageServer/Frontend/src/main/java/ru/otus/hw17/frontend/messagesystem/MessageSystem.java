package ru.otus.hw17.frontend.messagesystem;

import ru.otus.hw17.frontend.messagesystem.client.MsClient;
import ru.otus.hw17.frontend.messagesystem.message.Message;

public interface MessageSystem {

    void addClient(MsClient msClient);

    void removeClient(String clientId);

    boolean newMessage(Message msg);

    void dispose() throws InterruptedException;

    void dispose(Runnable callback) throws InterruptedException;

    void start();

    int currentQueueSize();
}

