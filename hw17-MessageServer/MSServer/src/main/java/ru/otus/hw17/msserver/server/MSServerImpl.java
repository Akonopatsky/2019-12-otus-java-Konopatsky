package ru.otus.hw17.msserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.messagesystem.MessageSystem;
import ru.otus.hw17.messagesystem.message.Message;
import ru.otus.hw17.messagesystem.message.MessageBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MSServerImpl implements MSServer{
    private final Logger logger = LoggerFactory.getLogger(MSServerImpl.class);
    private final MessageSystem messageSystem;
    private static final int PORT = 8090;

    public MSServerImpl(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }


    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (!Thread.currentThread().isInterrupted()) {
                logger.info("waiting for client connections");
                try(Socket clientSocket = serverSocket.accept())
                {
                    logger.info("Just connected to ", clientSocket.getRemoteSocketAddress());
                    handleClientConnection(clientSocket);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void stop() {

    }

    private void handleClientConnection(Socket clientSocket) {

        try (
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
        ) {
            Object input = null;
            while (!MessageBuilder.getVoidMessage().equals(input)) {
                input = inputStream.readObject();
                logger.info("read Object {}", input);
                Message message = (Message) input;
                if (input != null) {
                    logger.info("get message id {}, from {}, to {}", message.getId(), message.getFrom(), message.getTo());
//                    logger.info("get message ", input.toString());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
