package ru.otus.hw12.servlet;

import ru.otus.hw12.dao.UserDao;
import ru.otus.hw12.services.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyAdminServlet extends HttpServlet {
    private static final String USERS_PAGE_TEMPLATE = "admin.html";

    private final TemplateProcessor templateProcessor;
    private final UserDao userDao;


    public MyAdminServlet(TemplateProcessor templateProcessor, UserDao userDao) {
        this.userDao = userDao;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        resp.setContentType("text/html");
        resp.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        resp.setContentType("text/html");
        resp.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

}
