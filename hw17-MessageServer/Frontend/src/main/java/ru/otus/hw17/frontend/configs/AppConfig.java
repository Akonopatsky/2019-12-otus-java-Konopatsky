package ru.otus.hw17.frontend.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw17.frontend.messageSystemApp.dto.UserData;
import ru.otus.hw17.frontend.messageSystemApp.front.FrontendService;
import ru.otus.hw17.frontend.messageSystemApp.front.FrontendServiceImpl;
import ru.otus.hw17.frontend.messageSystemApp.front.handlers.GetUserDataResponseHandler;
import ru.otus.hw17.messagesystem.*;
import ru.otus.hw17.messagesystem.client.*;
import ru.otus.hw17.messagesystem.message.MessageType;
import ru.otus.hw17.msserver.socket.SocketClient;
import ru.otus.hw17.msserver.socket.SocketClientImpl;

import java.io.IOException;
import java.net.Socket;


@Configuration
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";
    private static final int PORT = 8090;
    private static final String HOST = "localhost";

    @Bean("callbackRegistry")
    public CallbackRegistry callbackRegistry() {
        return new CallbackRegistryImpl();
    }


    @Bean("requestHandlerFrontendStore")
    public HandlersStore requestHandlerFrontendStore(CallbackRegistry callbackRegistry) {
        HandlersStore requestHandlerFrontendStore = new HandlersStoreImpl();
        RequestHandler<UserData> requestHandler = new GetUserDataResponseHandler(callbackRegistry);
        requestHandlerFrontendStore.addHandler(MessageType.GET_ALL_USER, requestHandler);
        requestHandlerFrontendStore.addHandler(MessageType.SAVE_USER, requestHandler);
        return requestHandlerFrontendStore;
    }

    @Bean("frontendMsClient")
    public MsClient frontendMsClient(MessageSystem messageSystem, HandlersStore requestHandlerFrontendStore,
                                     CallbackRegistry callbackRegistry) {
        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME,
                messageSystem, requestHandlerFrontendStore, callbackRegistry);
        messageSystem.addClient(frontendMsClient);
        return frontendMsClient;
    }

    @Bean("databaseMsClient")
    public MsClient databaseMsClient(MessageSystem messageSystem, SocketClient socketClient) {
        MsClient databaseMsClient = new MsClientConnector(DATABASE_SERVICE_CLIENT_NAME, messageSystem, socketClient);
        messageSystem.addClient(databaseMsClient);
        socketClient.setMsClient(databaseMsClient);
        socketClient.start();
        return databaseMsClient;
    }

    @Bean(destroyMethod = "dispose")
    public MessageSystem messageSystem() {
        MessageSystem messageSystem = new MessageSystemImpl();
        messageSystem.start();
        return messageSystem;
    }

    @Bean("frontendService")
    public FrontendService frontendService(MsClient frontendMsClient) {
        return new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
    }

    @Bean("msSocketClient")
    public SocketClient msSocketClient(Socket socket) {
        return new SocketClientImpl(socket);
    }

    @Bean("socket")
    public Socket socket() {
        try {
            return new Socket(HOST, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
