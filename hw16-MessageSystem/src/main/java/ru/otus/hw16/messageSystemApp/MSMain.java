package ru.otus.hw16.messageSystemApp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.messageSystemApp.db.DBServiceImpl;
import ru.otus.hw16.messageSystemApp.db.handlers.GetUserDataRequestHandler;
import ru.otus.hw16.messageSystemApp.front.FrontendService;
import ru.otus.hw16.messageSystemApp.front.FrontendServiceImpl;
import ru.otus.hw16.messageSystemApp.front.handlers.GetUserDataResponseHandler;
import ru.otus.hw16.messagesystem.HandlersStore;
import ru.otus.hw16.messagesystem.HandlersStoreImpl;
import ru.otus.hw16.messagesystem.MessageSystem;
import ru.otus.hw16.messagesystem.MessageSystemImpl;
import ru.otus.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.hw16.messagesystem.client.CallbackRegistryImpl;
import ru.otus.hw16.messagesystem.client.MsClient;
import ru.otus.hw16.messagesystem.client.MsClientImpl;
import ru.otus.hw16.messagesystem.message.MessageType;

public class MSMain {
    private static final Logger logger = LoggerFactory.getLogger(MSMain.class);

    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    public static void main(String[] args) throws InterruptedException {
        MessageSystem messageSystem = new MessageSystemImpl();
        CallbackRegistry callbackRegistry = new CallbackRegistryImpl();

        HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(new DBServiceImpl()));
        MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME,
                messageSystem, requestHandlerDatabaseStore, callbackRegistry);
        messageSystem.addClient(databaseMsClient);

        HandlersStore requestHandlerFrontendStore = new HandlersStoreImpl();
        requestHandlerFrontendStore.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(callbackRegistry));

        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME,
                messageSystem, requestHandlerFrontendStore, callbackRegistry);
        FrontendService frontendService = new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
        messageSystem.addClient(frontendMsClient);

        frontendService.getUserData(1, data -> logger.info("got data:{}", data));
        frontendService.getUserData(2, data -> logger.info("got data:{}", data));

        Thread.sleep(100);
        messageSystem.dispose();
        logger.info("done");
    }
}
