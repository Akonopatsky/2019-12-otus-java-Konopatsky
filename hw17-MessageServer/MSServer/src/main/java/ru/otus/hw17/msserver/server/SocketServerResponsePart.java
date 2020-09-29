package ru.otus.hw17.msserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.messagesystem.MessageSystem;
import ru.otus.hw17.messagesystem.client.MsClient;
import ru.otus.hw17.messagesystem.message.Message;
import ru.otus.hw17.messagesystem.message.MessageBuilder;
import ru.otus.hw17.messagesystem.message.MessageType;
import ru.otus.hw17.msserver.socket.SocketClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServerResponsePart implements SocketClient {
    private static final Logger logger = LoggerFactory.getLogger(SocketServerResponsePart.class);
    private final Socket socket;
    private final MessageSystem messageSystem;
    private MsClient msClient;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;


    private ExecutorService processor = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable);
        thread.setName("msg-processor-thread");
        return thread;
    });

    public SocketServerResponsePart(Socket socket, MessageSystem messageSystem) {
        this.socket = socket;
        this.messageSystem = messageSystem;
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

    @Override
    public void setMsClient(MsClient msClient) {
        this.msClient = msClient;
    }

    @Override
    public boolean isReady() {
        return !socket.isClosed();
    }

    @Override
    public void start() {
        try {
            logger.info("start socketClient connected with msClient");
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            //processor.submit(() -> listening());
            listening();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            logger.info("Try to stop.");
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listening() {
        try {
            Object input = null;
            while (!Thread.currentThread().isInterrupted()) {
                input = inputStream.readObject();
                logger.info("read object {}", input.toString());
                Message msg = (Message) input;
                if (msg.getType().equals(MessageType.HANDSHAKE)) {
                    init(msg);
                } else {
                    handle(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handle(Message msg) {
        if (msg.getType().equals("handshake")) {
            logger.info("initializing by {}", msg.getFrom());
            init(msg);
        }
        else {
            msClient.handle(msg);
        }
    }

    private void init(Message msg) {
        logger.info("initialize server part MsClientConnector{} ", msg.getFrom());
        MsClient msClient = new MsClientConnector(msg.getFrom(), messageSystem, this);
        messageSystem.addClient(msClient);
        this.setMsClient(msClient);
        send(MessageBuilder.buildReplyMessage(msg,null));
    }
}
