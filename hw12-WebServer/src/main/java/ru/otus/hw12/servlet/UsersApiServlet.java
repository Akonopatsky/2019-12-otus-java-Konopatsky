package ru.otus.hw12.servlet;

import ru.otus.hw12.dao.UserDao;
import ru.otus.hw12.dao.hibernate.core.model.User;
import ru.otus.hw12.services.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UsersApiServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "admin.html";
    private static final String TEMPLATE_ATTR_CREATED_USER = "createdUser";
    private static final String TEMPLATE_ATTR_ALL_USERS = "allUsers";

    private final TemplateProcessor templateProcessor;
    private final UserDao userDao;

    private User lastSavedUser;

    public UsersApiServlet(TemplateProcessor templateProcessor, UserDao userDao) {
        this.userDao = userDao;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        User newUser = new User(name, age, address, phone);
        Map<String, Object> paramsMap = new HashMap<>();
        userDao.saveUser(newUser);
        lastSavedUser = newUser;
        putLastSavedUserInParams(paramsMap);
        resp.setContentType("text/html");
        resp.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        resp.setContentType("text/html");
        putAllUsersInParams(paramsMap);
        putLastSavedUserInParams(paramsMap);
        resp.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

    private void putLastSavedUserInParams(Map<String, Object> paramsMap) {
        paramsMap.put(TEMPLATE_ATTR_CREATED_USER, lastSavedUser);
    }

    private void putAllUsersInParams(Map<String, Object> paramsMap) {
        List<User> allUsers = userDao.getAllUsers();
        paramsMap.put(TEMPLATE_ATTR_ALL_USERS, allUsers);
    }
}
