package ru.otus.hw17.messagesystem.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.messagesystem.MessageSystem;
import ru.otus.hw17.messagesystem.message.Message;
import ru.otus.hw17.messagesystem.message.MessageBuilder;
import ru.otus.hw17.messagesystem.message.MessageType;
import ru.otus.hw17.msserver.socket.SocketClient;

import java.util.Objects;

public class MsClientConnector implements MsClient {
    private static final Logger logger = LoggerFactory.getLogger(MsClientConnector.class);

    private final MessageSystem messageSystem;
    private final SocketClient socketClient;

    private final String name;

    public MsClientConnector(String name, MessageSystem messageSystem,  SocketClient socketClient) {
        this.socketClient = socketClient;
        this.name = name;
        this.messageSystem = messageSystem;
    }

    @Override
    public boolean sendMessage(Message msg) {
        if (socketClient.isReady()) {
            logger.info("new message id {} from {} to {} ", msg.getId(), msg.getFrom(), msg.getTo());
            socketClient.send(msg);
            return true;
        } else {
            logger.warn("socketClient is not ready");
            return false;
        }
    }

    @Override
    public void handle(Message msg) {
        if (getName().equals(msg.getTo())) {
            sendMessage(msg);
        }
        else {
            messageSystem.newMessage(msg);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public <T extends ResultDataType> Message produceMessage(String to, T data, MessageType msgType, MessageCallback<T> callback) {
        Message message = MessageBuilder.buildMessage(name, to, null, data, msgType);
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MsClientConnector msClient = (MsClientConnector) o;
        return Objects.equals(name, msClient.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
