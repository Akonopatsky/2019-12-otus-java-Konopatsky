package ru.otus.hw17.msserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.messagesystem.MessageSystem;
import ru.otus.hw17.messagesystem.client.MsClient;
import ru.otus.hw17.messagesystem.message.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageSystemClientPart implements MessageSystem, MessageServerClient {
    private final Logger logger = LoggerFactory.getLogger(MessageSystemClientPart.class);
    private final Map<String, MsClient> clientMap = new ConcurrentHashMap<>();
    private final MSServerConnector msServerConnector;

    public MessageSystemClientPart(MSServerConnector msServerConnector) {
        this.msServerConnector = msServerConnector;
    }

    @Override
    public void addClient(MsClient msClient) {
        logger.info("new client:{}", msClient.getName());
        if (clientMap.containsKey(msClient.getName())) {
            throw new IllegalArgumentException("Error. client: " + msClient.getName() + " already exists");
        }
        clientMap.put(msClient.getName(), msClient);
    }

    @Override
    public void removeClient(String clientId) {
        MsClient removedClient = clientMap.remove(clientId);
        if (removedClient == null) {
            logger.warn("client not found: {}", clientId);
        } else {
            logger.info("removed client:{}", removedClient);
        }
    }

    @Override
    public boolean newMessage(Message msg) {
        if (msServerConnector.isReady()) {
            logger.info("new message id {} from {} to {} ", msg.getId(), msg.getFrom(), msg.getTo());
            send(msg);
            return true;
        } else {
            logger.warn("Connection is not ready");
            return false;
        }
    }

    @Override
    public void dispose() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void dispose(Runnable callback) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void start() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int currentQueueSize() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void send(Message msg) {
        msServerConnector.send(msg);
    }

    @Override
    public void handle(Message msg) {
        MsClient msClient = clientMap.get(msg.getTo());
        if (msClient == null) {
            logger.warn("client {} not found", msg.getTo());
        } else {
            try {
                msClient.handle(msg);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                logger.error("message:{}", msg);
            }
        }
    }
}
