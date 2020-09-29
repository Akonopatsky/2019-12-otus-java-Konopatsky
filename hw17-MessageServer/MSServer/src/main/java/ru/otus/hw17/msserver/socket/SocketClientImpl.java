package ru.otus.hw17.msserver.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.messagesystem.client.MsClient;
import ru.otus.hw17.messagesystem.message.Message;
import ru.otus.hw17.messagesystem.message.MessageBuilder;
import ru.otus.hw17.messagesystem.message.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketClientImpl implements SocketClient {
    private static final Logger logger = LoggerFactory.getLogger(SocketClientImpl.class);
    private final Socket socket;
    private MsClient msClient;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean isReady = false;


    private ExecutorService processor = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable);
        thread.setName("msg-processor-thread");
        return thread;
    });

    public SocketClientImpl(Socket socket) {
        this.socket = socket;
    }

    public void send(Message msg) {
        try {
            outputStream.writeObject(msg);
            outputStream.flush();
            logger.info("has sent message {}", msg.toString());
        } catch (IOException e) {
            logger.error("Send message error cause by {} ", e);
        }
    }

    @Override
    public void setMsClient(MsClient msClient) {
        this.msClient = msClient;
    }

    @Override
    public boolean isReady() {
        return isReady;
    }

    @Override
    public void start() {
        try {
            logger.info("start socketClient connected with msClient {} ", msClient.getName());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            processor.submit(() -> listening());
            handshake();
        } catch (Exception e) {
            logger.error("Start socketClient error cause by {} ", e);
        }
    }

    private void handshake() {
        Message handShakeMessage = MessageBuilder.buildMessage(msClient.getName(), "MsServer", null, null, MessageType.HANDSHAKE);
        send(handShakeMessage);
    }

    @Override
    public void stop() {
        try {
            logger.info("Try to stop.");
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            logger.error("Stop socketClient error cause by {} ", e);
        }
    }

    private void listening() {
        try {
            Object input = null;
            while (!Thread.currentThread().isInterrupted()) {
                input = inputStream.readObject();
                logger.info("read object {}", input.toString());
                Message msg = (Message) input;
                if (msg.getType().equals("handshake")) {
                    isReady = true;
                }
                else {
                    msClient.handle(msg);
                }
            }
        } catch (Exception e) {
            logger.error("Listening socketClient error cause by {} ", e);
        }
    }
}
