package ru.otus.hw12;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.SessionFactory;
import ru.otus.hw12.dao.UserDao;
import ru.otus.hw12.dao.UserDaoAdapter;
import ru.otus.hw12.helpers.FileSystemHelper;
import ru.otus.hw12.hibernate.core.model.Address;
import ru.otus.hw12.hibernate.core.model.Phone;
import ru.otus.hw12.hibernate.core.model.User;
import ru.otus.hw12.hibernate.hibernate.HibernateUtils;
import ru.otus.hw12.hibernate.hibernate.dao.UserDaoHibernate;
import ru.otus.hw12.hibernate.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.hw12.server.UsersWebServer;
import ru.otus.hw12.server.UsersWebServerWithBasicSecurity;
import ru.otus.hw12.services.InMemoryLoginServiceImpl;
import ru.otus.hw12.services.TemplateProcessor;
import ru.otus.hw12.services.TemplateProcessorImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerWithBasicSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";

    public static void main(String[] args) throws Exception {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(
                "hibernate.cfg.xml", User.class, Address.class, Phone.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDaoHibernate userDaoHibernate = new UserDaoHibernate(sessionManager);
        UserDao userDao = new UserDaoAdapter(userDaoHibernate);
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
      //LoginService loginService = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);
        LoginService loginService = new InMemoryLoginServiceImpl(userDao);

        UsersWebServer usersWebServer = new UsersWebServerWithBasicSecurity(WEB_SERVER_PORT,
                loginService, userDao, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
