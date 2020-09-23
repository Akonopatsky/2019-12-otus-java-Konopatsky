package ru.otus.hw17.msserver.server;

import ru.otus.hw17.messagesystem.client.MsClient;
import ru.otus.hw17.messagesystem.message.Message;

public class MSServerConnectorImpl implements MSServerConnector{
    private MsClient msClient;

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void send(Message message) {

    }

    @Override
    public boolean isConnection() {
        return false;
    }
}
