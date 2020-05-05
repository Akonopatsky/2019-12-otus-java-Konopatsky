package ru.otus.hw12.dao;

import ru.otus.hw12.hibernate.core.dao.UserDao;
import ru.otus.hw12.hibernate.core.model.User;

import java.util.List;

public interface UserDaoWebServer extends UserDao {

    List<User> getAllUsers();

}