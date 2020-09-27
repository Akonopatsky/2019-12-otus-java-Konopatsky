package ru.otus.hw17.msserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.messagesystem.message.Message;
import ru.otus.hw17.messagesystem.message.MessageBuilder;
import ru.otus.hw17.msserver.socket.MonoThreadClientHandler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MSServerImpl2 implements MSServer{
    private static final int PORT = 8090;
    private final Logger logger = LoggerFactory.getLogger(MSServerImpl2.class);

    static ExecutorService executeIt = Executors.newFixedThreadPool(2);

    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (!Thread.currentThread().isInterrupted()) {
                logger.info("waiting for client connection");
               Socket socket = serverSocket.accept();
                    logger.info("socket in start {} ", socket.isClosed());
                   // handleClientConnection1(socket);
                    executeIt.execute(new MonoThreadClientHandler(socket));


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void stop() {
        logger.info("Try to stop.");
    }



    private void handleClientConnection1(Socket socket) {
        try (
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        ) {
            Object input = null;
            while (!MessageBuilder.getVoidMessage().equals(input)) {
                input = inputStream.readObject();
                logger.info("read Object {}", input);
                Message message = (Message) input;
                if (input != null) {
                    logger.info("get message id {}, from {}, to {}", message.getId(), message.getFrom(), message.getTo());

                    Thread.currentThread().sleep(TimeUnit.SECONDS.toMillis(2));
                    outputStream.writeObject(message);
//                    logger.info("get message ", input.toString());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
