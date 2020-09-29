package ru.otus.hw17.frontend.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw17.frontend.messageSystemApp.front.FrontendService;
import ru.otus.hw17.frontend.messageSystemApp.front.FrontendServiceImpl;
import ru.otus.hw17.frontend.messageSystemApp.front.handlers.GetUserDataResponseHandler;
import ru.otus.hw17.messagesystem.HandlersStore;
import ru.otus.hw17.messagesystem.HandlersStoreImpl;
import ru.otus.hw17.messagesystem.RequestHandler;
import ru.otus.hw17.messagesystem.client.CallbackRegistry;
import ru.otus.hw17.messagesystem.client.CallbackRegistryImpl;
import ru.otus.hw17.messagesystem.client.MsClient;
import ru.otus.hw17.messagesystem.client.MsClientSocketConnector;
import ru.otus.hw17.messagesystem.message.MessageType;
import ru.otus.hw17.msserver.dto.UserData;
import ru.otus.hw17.msserver.socket.SocketClient;
import ru.otus.hw17.msserver.socket.SocketClientImpl;

import java.io.IOException;
import java.net.Socket;


@Configuration
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    @Value("${frontend.service.name}")
    private String frontendServiceName;
    @Value("${database.service.name}")
    private String databaseServiceName;
    @Value("${msServer.port}")
    private int port;
    @Value("${msServer.address}")
    private String host;

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
    public MsClient frontendMsClient(SocketClient msSocketClient, HandlersStore requestHandlerFrontendStore,
                                     CallbackRegistry callbackRegistry) {
        MsClient frontendMsClient = new MsClientSocketConnector(frontendServiceName,
                msSocketClient, requestHandlerFrontendStore, callbackRegistry);
        msSocketClient.setMsClient(frontendMsClient);
        msSocketClient.start();
        return frontendMsClient;
    }

    @Bean("frontendService")
    public FrontendService frontendService(MsClient frontendMsClient) {
        return new FrontendServiceImpl(frontendMsClient, databaseServiceName);
    }

    @Bean("msSocketClient")
    public SocketClient msSocketClient(Socket socket) {
        return new SocketClientImpl(socket);
    }

    @Bean("socket")
    public Socket socket() {
        try {
            return new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
