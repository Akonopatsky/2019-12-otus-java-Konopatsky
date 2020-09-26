package ru.otus.hw17.msserver;

import ru.otus.hw17.messagesystem1.MessageSystem;
import ru.otus.hw17.messagesystem1.MessageSystemImpl;
import ru.otus.hw17.msserver.server.MSServer;
import ru.otus.hw17.msserver.server.MSServerImpl;

public class MSServerMain {
    public static void main(String[] args) {
        MessageSystem messageSystem = new MessageSystemImpl();
        MSServer msServer = new MSServerImpl(messageSystem);
        msServer.start();
    }
}
