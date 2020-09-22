package ru.otus.hw17.msserver;

import ru.otus.hw17.msserver.server.MSServer;
import ru.otus.hw17.msserver.server.MSServerImpl;

public class MSServerMain {
    public static void main(String[] args) {
        MSServer msServer = new MSServerImpl();
        msServer.start();
    }
}
