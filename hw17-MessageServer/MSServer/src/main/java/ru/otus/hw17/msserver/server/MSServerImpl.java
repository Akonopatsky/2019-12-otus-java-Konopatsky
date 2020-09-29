package ru.otus.hw17.msserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.messagesystem.MessageSystem;
import ru.otus.hw17.messagesystem.message.MessageBuilder;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MSServerImpl implements MSServer {
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

    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (!Thread.currentThread().isInterrupted()) {
                logger.info("waiting for client connection");
                Socket socket = serverSocket.accept();
                logger.info("socket in start {} ", socket.isClosed());
                socketHandler.submit(() -> handleClientConnection(socket));
            }
        } catch (Exception ex) {
            logger.error("start server error {} ", ex);
        }
    }

    @Override
    public void stop() {
        logger.info("Try to stop.");
        messageSystem.newMessage(MessageBuilder.getVoidMessage());
    }

    private void handleClientConnection(Socket socket) {
        ConnectionHandler socketClient = new ConnectionHandler(socket, messageSystem);
        socketClient.start();
    }
}
