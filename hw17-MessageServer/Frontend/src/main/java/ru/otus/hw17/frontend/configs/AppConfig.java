package ru.otus.hw17.frontend.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw17.frontend.messageSystemApp.dto.UserData;
import ru.otus.hw17.frontend.messageSystemApp.front.FrontendService;
import ru.otus.hw17.frontend.messageSystemApp.front.FrontendServiceImpl;
import ru.otus.hw17.frontend.messageSystemApp.front.handlers.GetUserDataResponseHandler;
import ru.otus.hw17.frontend.messagesystem.*;
import ru.otus.hw17.frontend.messagesystem.client.CallbackRegistry;
import ru.otus.hw17.frontend.messagesystem.client.CallbackRegistryImpl;
import ru.otus.hw17.frontend.messagesystem.client.MsClient;
import ru.otus.hw17.frontend.messagesystem.client.MsClientImpl;
import ru.otus.hw17.frontend.messagesystem.message.MessageType;


@Configuration
public class AppConfig {
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

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

}
