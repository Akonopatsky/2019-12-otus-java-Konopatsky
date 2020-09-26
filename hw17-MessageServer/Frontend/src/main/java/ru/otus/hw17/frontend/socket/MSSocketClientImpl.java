package ru.otus.hw17.frontend.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.frontend.messagesystem.message.Message;

public class MSSocketClientImpl implements MSSocketClient {
    private static final int PORT = 8090;
    private static final String HOST = "localhost";
    private static final Logger logger = LoggerFactory.getLogger(MSSocketClientImpl.class);


    @Override
    public void send(Message msg) {

    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
