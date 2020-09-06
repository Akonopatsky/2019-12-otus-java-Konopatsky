package ru.otus.hw16.messageSystemApp;


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.dataaccsess.cachehw.HwCache;
import ru.otus.hw16.dataaccsess.cachehw.MyCache;
import ru.otus.hw16.dataaccsess.core.dao.UserDao;
import ru.otus.hw16.dataaccsess.core.model.Address;
import ru.otus.hw16.dataaccsess.core.model.Phone;
import ru.otus.hw16.dataaccsess.core.model.User;
import ru.otus.hw16.dataaccsess.core.service.DBServiceUser;
import ru.otus.hw16.dataaccsess.core.service.DbServiceUserImplCache;
import ru.otus.hw16.dataaccsess.hibernate.HibernateUtils;
import ru.otus.hw16.dataaccsess.hibernate.dao.UserDaoHibernate;
import ru.otus.hw16.dataaccsess.hibernate.sessionmanager.SessionManagerHibernate;
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

public class MSMain {
    private static final Logger logger = LoggerFactory.getLogger(MSMain.class);
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    public static void main(String[] args) throws InterruptedException {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(
                "hibernate.cfg.xml", User.class, Address.class, Phone.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        HwCache cache = new MyCache();
        DBServiceUser dbService = new DbServiceUserImplCache(userDao, cache);

        MessageSystem messageSystem = new MessageSystemImpl();
        CallbackRegistry callbackRegistry = new CallbackRegistryImpl();

        HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.SAVE_USER, new SaveUserDataRequestHandler(dbService));
        requestHandlerDatabaseStore.addHandler(MessageType.GET_ALL_USER, new GetAllUserDataRequestHandler(dbService));
        MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME,
                messageSystem, requestHandlerDatabaseStore, callbackRegistry);
        messageSystem.addClient(databaseMsClient);

        HandlersStore requestHandlerFrontendStore = new HandlersStoreImpl();
        RequestHandler requestHandler = new GetUserDataResponseHandler(callbackRegistry);
        requestHandlerFrontendStore.addHandler(MessageType.GET_ALL_USER, requestHandler);
        requestHandlerFrontendStore.addHandler(MessageType.SAVE_USER, requestHandler);

        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME,
                messageSystem, requestHandlerFrontendStore, callbackRegistry);
        FrontendService frontendService = new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
        messageSystem.addClient(frontendMsClient);


/*        User user1 = new User("Вася", 15, "Москва", "+7911 111 111" );
        User user2 = new User("Петя", 16, "Тула", "+7911 111 222" );
        dbService.saveUser(user1);
        dbService.saveUser(user2);
        logger.info("save 2 user ______________________________");
        logger.info("all users: {}", dbService.getAllUsers() );
        frontendService.getAllUsers(data -> logger.info("all users: {}", data.getData()));*/

        frontendService.saveUser(new User("Вася", 15, "Москва", "+7911 111 111" ), data -> logger.info("saved user: {}", data.getData()));
 //       frontendService.saveUser(new User("Петя", 16, "Тула", "+7911 111 222" ), data -> logger.info("saved user: {}", data.getData()));
        Thread.sleep(100);
        frontendService.getAllUsers(data -> logger.info("all users: {}", data.getData()));


        Thread.sleep(200);
        messageSystem.dispose();
        logger.info("done");
    }
}
