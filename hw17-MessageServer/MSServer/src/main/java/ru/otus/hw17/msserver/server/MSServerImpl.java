package ru.otus.hw17.msserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.messagesystem.MessageSystem;
import ru.otus.hw17.messagesystem.client.MsClientConnector;
import ru.otus.hw17.messagesystem.client.MsClient;
import ru.otus.hw17.messagesystem.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MSServerImpl {
    private static final int PORT = 8090;
    private static final int SOCKET_HANDLER_THREAD_LIMIT = 6;
    private final Logger logger = LoggerFactory.getLogger(MSServerImpl.class);
    private final MessageSystem messageSystem;

    private final ExecutorService socketHandler = Executors.newFixedThreadPool(SOCKET_HANDLER_THREAD_LIMIT,
            new ThreadFactory() {
                private final AtomicInteger threadNameSeq = new AtomicInteger(0);
                @Override
                public Thread newThread(Runnable runnable) {
                    Thread thread = new Thread(runnable);
                    thread.setName("socket-handler-thread-" + threadNameSeq.incrementAndGet());
                    return thread;
                }
            });

    public MSServerImpl(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (!Thread.currentThread().isInterrupted()) {
                try (Socket clientSocket = serverSocket.accept()) {
                    socketHandler.submit(() -> handleClientConnection(clientSocket));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stop() {
        logger.info("Try to stop.");
    }

    private void handleClientConnection(Socket socket) {
        try {
            logger.info("handleClientConnection invoked");
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            MsClient msClient = new MsClientConnector("test", messageSystem, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listening(ObjectInputStream inputStream, MsClient msClient) {
        try {

            Object input = null;
            while (!Thread.currentThread().isInterrupted()) {
                input = inputStream.readObject();
                logger.info("read object {}", input.toString());
                Message msg = (Message) input;
                msClient.handle(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
