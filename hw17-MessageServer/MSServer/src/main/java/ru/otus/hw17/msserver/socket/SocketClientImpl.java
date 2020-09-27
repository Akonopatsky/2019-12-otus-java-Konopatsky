package ru.otus.hw17.msserver.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.messagesystem.client.MsClient;
import ru.otus.hw17.messagesystem.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketClientImpl implements SocketClient {
    private static final Logger logger = LoggerFactory.getLogger(SocketClientImpl.class);
    private MsClient msClient;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private final Socket socket;

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
            logger.info("start socketClient connected with msClient {} ", msClient.getName());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            processor.submit(() -> listening());
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
                msClient.handle(msg);
                msClient.
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
