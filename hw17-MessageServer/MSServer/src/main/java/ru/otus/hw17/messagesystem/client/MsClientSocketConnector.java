package ru.otus.hw17.messagesystem.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.messagesystem.HandlersStore;
import ru.otus.hw17.messagesystem.RequestHandler;
import ru.otus.hw17.messagesystem.message.Message;
import ru.otus.hw17.messagesystem.message.MessageBuilder;
import ru.otus.hw17.messagesystem.message.MessageType;
import ru.otus.hw17.msserver.socket.SocketClient;

import java.util.Objects;

public class MsClientSocketConnector implements MsClient{
    private static final Logger logger = LoggerFactory.getLogger(MsClientSocketConnector.class);
    private final SocketClient socketClient;
    private final String name;
    private final HandlersStore handlersStore;
    private final CallbackRegistry callbackRegistry;

    public MsClientSocketConnector(String name, SocketClient socketClient, HandlersStore handlersStore, CallbackRegistry callbackRegistry) {
        this.socketClient = socketClient;
        this.name = name;
        this.handlersStore = handlersStore;
        this.callbackRegistry = callbackRegistry;
    }

    @Override
    public boolean sendMessage(Message msg) {
        if (socketClient.isReady()) {
            logger.info(" send {} from {} to {} ", msg.getId(), msg.getFrom(), msg.getTo());
            socketClient.send(msg);
            return true;
        } else {
            logger.warn("socketClient is not ready");
            return false;
        }
    }

    @Override
    public void handle(Message msg) {
        logger.info("new message:{}", msg);
        try {
            RequestHandler requestHandler = handlersStore.getHandlerByType(msg.getType());
            if (requestHandler != null) {
                logger.info("requestHandler.handle {} ", requestHandler.toString() );
                requestHandler.handle(msg).ifPresent(message -> sendMessage((Message) message));
            } else {
                logger.error("handler not found for the message type:{}", msg.getType());
            }
        } catch (Exception ex) {
            logger.error("msg:{}", msg, ex);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public <T extends ResultDataType> Message produceMessage(String to, T data, MessageType msgType, MessageCallback<T> callback) {
        Message message = MessageBuilder.buildMessage(name, to, null, data, msgType);
        callbackRegistry.put(message.getCallbackId(), callback);
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MsClientSocketConnector msClient = (MsClientSocketConnector) o;
        return Objects.equals(name, msClient.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
