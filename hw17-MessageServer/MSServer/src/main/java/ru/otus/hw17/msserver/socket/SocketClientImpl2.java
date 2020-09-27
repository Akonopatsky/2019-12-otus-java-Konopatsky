package ru.otus.hw17.msserver.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.messagesystem.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientImpl2 implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(SocketClientImpl2.class);
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private static Socket socket;

    public SocketClientImpl2(Socket socket) {
        SocketClientImpl2.socket = socket;
    }

    public void send(Message msg) {
        try {
            outputStream.writeObject(msg);
            outputStream.flush();
            logger.info("has sent message {}", msg.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isReady() {
        return !socket.isClosed();
    }

    @Override
    public void run() {
        try {
            logger.info("socket closed {} ", socket.isClosed());
            logger.info("socket.isClosed 1 {}", socket.isClosed());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            logger.info("socket.isClosed 2 {}", socket.isClosed());
            logger.info("socket.isClosed 2 {}", socket.isClosed());
            logger.info("socket.isClosed 2 {}", socket.isClosed());
            Object input = null;
            while (Thread.currentThread().isInterrupted()) {
                input = inputStream.readObject();
                logger.info("read object {}", input.toString());
                Message msg = (Message) input;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
