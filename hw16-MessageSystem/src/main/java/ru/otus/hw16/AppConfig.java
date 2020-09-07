package ru.otus.hw16;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw16.dataaccsess.cachehw.MyCache;
import ru.otus.hw16.dataaccsess.core.model.Address;
import ru.otus.hw16.dataaccsess.core.model.Phone;
import ru.otus.hw16.dataaccsess.core.model.User;
import ru.otus.hw16.dataaccsess.core.service.DBServiceUser;
import ru.otus.hw16.dataaccsess.hibernate.HibernateUtils;
import ru.otus.hw16.messageSystemApp.db.handlers.GetAllUserDataRequestHandler;
import ru.otus.hw16.messageSystemApp.db.handlers.SaveUserDataRequestHandler;
import ru.otus.hw16.messageSystemApp.front.FrontendService;
import ru.otus.hw16.messageSystemApp.front.FrontendServiceImpl;
import ru.otus.hw16.messageSystemApp.front.handlers.GetUserDataResponseHandler;
import ru.otus.hw16.messagesystem.*;
import ru.otus.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.hw16.messagesystem.client.CallbackRegistryImpl;
import ru.otus.hw16.messagesystem.client.MsClient;
import ru.otus.hw16.messagesystem.client.MsClientImpl;
import ru.otus.hw16.messagesystem.message.MessageType;

@Configuration
public class AppConfig {
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtils.buildSessionFactory(
                "hibernate.cfg.xml", User.class, Address.class, Phone.class);
    }

    @Bean
    public MyCache myCache() {
        return new MyCache();
    }

    @Bean
    public CallbackRegistry callbackRegistry() {
        return new CallbackRegistryImpl();
    }

    @Bean("requestHandlerDatabaseStore")
    public HandlersStore requestHandlerDatabaseStore(DBServiceUser dbService) {
        HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.SAVE_USER, new SaveUserDataRequestHandler(dbService));
        requestHandlerDatabaseStore.addHandler(MessageType.GET_ALL_USER, new GetAllUserDataRequestHandler(dbService));
        return requestHandlerDatabaseStore;
    }

    @Bean("requestHandlerDatabaseStore")
    public HandlersStore requestHandlerFrontendStore(CallbackRegistry callbackRegistry) {
        HandlersStore requestHandlerFrontendStore = new HandlersStoreImpl();
        RequestHandler requestHandler = new GetUserDataResponseHandler(callbackRegistry);
        requestHandlerFrontendStore.addHandler(MessageType.GET_ALL_USER, requestHandler);
        requestHandlerFrontendStore.addHandler(MessageType.SAVE_USER, requestHandler);
        return requestHandlerFrontendStore;
    }

    @Bean("databaseMsClient")
    public MsClient databaseMsClient(MessageSystem messageSystem, HandlersStore requestHandlerDatabaseStore,
                                     CallbackRegistry callbackRegistry) {
        MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME,
                messageSystem, requestHandlerDatabaseStore, callbackRegistry);
        messageSystem.addClient(databaseMsClient);
        return databaseMsClient;
    }

    @Bean("frontendMsClient")
    public MsClient frontendMsClient(MessageSystem messageSystem, HandlersStore requestHandlerFrontendStore,
                                     CallbackRegistry callbackRegistry) {
        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME,
                messageSystem, requestHandlerFrontendStore, callbackRegistry);
        messageSystem.addClient(frontendMsClient);
        return frontendMsClient;
    }

    @Bean
    public MessageSystem messageSystem() {
        MessageSystem messageSystem = new MessageSystemImpl();
        messageSystem.start();
        return messageSystem;
    }

    @Bean
    public FrontendService frontendService(MsClient frontendMsClient) {
        FrontendService frontendService = new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
        return frontendService;
    }

}
