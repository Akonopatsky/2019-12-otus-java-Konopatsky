package ru.otus.hw17.frontend.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.messagesystem.message.Message;
import ru.otus.hw17.messagesystem.message.MessageBuilder;
import ru.otus.hw17.messagesystem.message.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;


public class FrontendSocketClient {
    private final Logger logger = LoggerFactory.getLogger(FrontendSocketClient.class);
    private static final int PORT = 8090;
    private static final String HOST = "localhost";

    public void start() {
        try {
            try (Socket clientSocket = new Socket(HOST, PORT)) {
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                for (int idx = 0; idx < 100; idx++) {
                    Message message = MessageBuilder.buildMessage(
                            "from"+idx,
                            "to1",
                            null,
                            null,
                            MessageType.GET_ALL_USER);
                    logger.info("sending message {}");
                    outputStream.writeObject(message);

                    Thread.sleep(TimeUnit.SECONDS.toMillis(3));

                    System.out.println();
                }

                System.out.println("\nstop communication");
                //               outputStream.println("stop");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
