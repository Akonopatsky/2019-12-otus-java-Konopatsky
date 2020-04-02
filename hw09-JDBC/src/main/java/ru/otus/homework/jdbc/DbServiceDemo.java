package ru.otus.homework.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.jdbc.DIY.GenericDao;
import ru.otus.homework.jdbc.DIY.JdbcGenerator;
import ru.otus.homework.jdbc.core.dao.UserDao;
import ru.otus.homework.jdbc.core.model.User;
import ru.otus.homework.jdbc.core.service.DBServiceUser;
import ru.otus.homework.jdbc.core.service.DbServiceUserImpl;
import ru.otus.homework.jdbc.h2.DataSourceH2;
import ru.otus.homework.jdbc.jdbc.DbExecutor;
import ru.otus.homework.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class DbServiceDemo {
  private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

  public static void main(String[] args) throws Exception {
    DataSource dataSource = new DataSourceH2();
    DbServiceDemo demo = new DbServiceDemo();

    demo.createTable(dataSource);

    SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
    DbExecutor<User> dbExecutor = new DbExecutor<>();
    UserDao<User> userDao = new GenericDao(sessionManager, dbExecutor, new JdbcGenerator(new User()));

    DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
    long id = dbServiceUser.saveUser(new User(6, "dbServiceUser",34));
    Optional<User> user = dbServiceUser.getUser(id);

    System.out.println(user);
    user.ifPresentOrElse(
        crUser -> logger.info("created user, name:{}", crUser.getName()),
        () -> logger.info("user was not created")
    );

  }

  private void createTable(DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table User(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
      pst.executeUpdate();
    }
    System.out.println("table created");
  }

}
