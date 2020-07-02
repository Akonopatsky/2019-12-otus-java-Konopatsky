package ru.otus.hw12;

import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.SessionFactory;
import ru.otus.hw12.dataaccsess.core.service.DBServiceUser;
import ru.otus.hw12.dataaccsess.core.service.DbServiceUserImplCache;
import ru.otus.hw12.helpers.FileSystemHelper;
import ru.otus.hw12.dataaccsess.core.model.Address;
import ru.otus.hw12.dataaccsess.core.model.Phone;
import ru.otus.hw12.dataaccsess.core.model.User;
import ru.otus.hw12.dataaccsess.hibernate.HibernateUtils;
import ru.otus.hw12.dataaccsess.hibernate.dao.UserDaoHibernate;
import ru.otus.hw12.dataaccsess.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.hw12.server.UsersWebServer;
import ru.otus.hw12.server.UsersWebServerWithBasicSecurity;
import ru.otus.hw12.services.TemplateProcessor;
import ru.otus.hw12.services.TemplateProcessorImpl;

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
DBServiceUser DBServiceUser = new DbServiceUserImplCache(userDaoHibernate);
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
        LoginService loginService = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);
        UsersWebServer usersWebServer = new UsersWebServerWithBasicSecurity(WEB_SERVER_PORT,
                loginService, DBServiceUser, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
