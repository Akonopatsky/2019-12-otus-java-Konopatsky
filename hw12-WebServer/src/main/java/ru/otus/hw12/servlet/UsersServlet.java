package ru.otus.hw12.servlet;

import ru.otus.hw12.dataaccsess.core.service.DBServiceUser;
import ru.otus.hw12.dataaccsess.core.model.User;
import ru.otus.hw12.services.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UsersServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "admin.html";
    private static final String TEMPLATE_ATTR_ALL_USERS = "allUsers";

    private final TemplateProcessor templateProcessor;
    private final DBServiceUser dbServiceUser;

    public UsersServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        User newUser = new User(name, age, address, phone);
        dbServiceUser.saveUser(newUser);
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        resp.setContentType("text/html");
        putAllUsersInParams(paramsMap);
        resp.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

    private void putAllUsersInParams(Map<String, Object> paramsMap) {
        List<User> allUsers = dbServiceUser.getAllUsers();
        paramsMap.put(TEMPLATE_ATTR_ALL_USERS, allUsers);
    }
}
