package ru.otus.hw17.dbclient;

import org.hibernate.SessionFactory;
import ru.otus.hw17.dataaccsess.cachehw.MyCache;
import ru.otus.hw17.dataaccsess.core.model.Address;
import ru.otus.hw17.dataaccsess.core.model.Phone;
import ru.otus.hw17.dataaccsess.core.model.User;
import ru.otus.hw17.dataaccsess.core.service.DBServiceUser;
import ru.otus.hw17.dataaccsess.core.service.DbServiceUserImplCache;
import ru.otus.hw17.dataaccsess.hibernate.HibernateUtils;
import ru.otus.hw17.dataaccsess.hibernate.dao.UserDaoHibernate;
import ru.otus.hw17.dataaccsess.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.hw17.messagesystem.HandlersStore;
import ru.otus.hw17.messagesystem.HandlersStoreImpl;
import ru.otus.hw17.messagesystem.client.CallbackRegistry;
import ru.otus.hw17.messagesystem.client.CallbackRegistryImpl;
import ru.otus.hw17.messagesystem.client.MsClient;
import ru.otus.hw17.messagesystem.client.MsClientConnector2;
import ru.otus.hw17.messagesystem.message.MessageType;
import ru.otus.hw17.msserver.socket.SocketClient;
import ru.otus.hw17.msserver.socket.SocketClientImpl;

import java.io.IOException;
import java.net.Socket;

public class DataAccessSystem {
    private  final String databaseServiceClientName;
    private  final int port;
    private  final String hostName;

    public DataAccessSystem(String databaseServiceClientName, int port, String hostName) {
        this.databaseServiceClientName = databaseServiceClientName;
        this.port = port;
        this.hostName = hostName;
    }

    public void start() {
        try {
            MyCache<String, User> myCache  = new MyCache<>();
            SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(
                    "hibernate.cfg.xml", User.class, Address.class, Phone.class);
            SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
            UserDaoHibernate userDaoHibernate = new UserDaoHibernate(sessionManager);
            DBServiceUser dbServiceUser = new DbServiceUserImplCache(userDaoHibernate, myCache);
            HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
            requestHandlerDatabaseStore.addHandler(MessageType.SAVE_USER, new SaveUserDataRequestHandler(dbService));
            requestHandlerDatabaseStore.addHandler(MessageType.GET_ALL_USER, new GetAllUserDataRequestHandler(dbService));
            CallbackRegistry callbackRegistry = new CallbackRegistryImpl();
            Socket socket = new Socket(hostName, port);
            SocketClient socketClient = new SocketClientImpl(socket);
            MsClient databaseMsClient = new MsClientConnector2(databaseServiceClientName,
                    socketClient, requestHandlerDatabaseStore, callbackRegistry);
            socketClient.setMsClient(databaseMsClient);
            socketClient.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
