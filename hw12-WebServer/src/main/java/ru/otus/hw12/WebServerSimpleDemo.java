package ru.otus.hw12;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.otus.hw12.dao.InMemoryUserDao;
import ru.otus.hw12.dao.UserDao;
import ru.otus.hw12.server.UsersWebServer;
import ru.otus.hw12.server.UsersWebServerSimple;
import ru.otus.hw12.services.*;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerSimpleDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        UserDao userDao = new InMemoryUserDao();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        UsersWebServer usersWebServer = new UsersWebServerSimple(WEB_SERVER_PORT, userDao,
                gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
