package ru.otus.homework.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.jdbc.DIY.JdbcGenerator;
import ru.otus.homework.jdbc.core.dao.DaoInterface;
import ru.otus.homework.jdbc.core.model.Account;
import ru.otus.homework.jdbc.core.model.User;

import ru.otus.homework.jdbc.DIY.JdbcTamplate;
import ru.otus.homework.jdbc.core.service.DBServiceUser;
import ru.otus.homework.jdbc.core.service.DbServiceUserImpl;
import ru.otus.homework.jdbc.h2.DataSourceH2;
import ru.otus.homework.jdbc.jdbc.DbExecutor;
import ru.otus.homework.jdbc.jdbc.dao.DiyUserDaoJdbc;
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
    DaoInterface<User> userDao = new DiyUserDaoJdbc(sessionManager, dbExecutor, new JdbcGenerator(new User()));
    new JdbcGenerator<>(new Account());

    DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
  //  JdbcTamplate<User> userMapper = new JdbcTamplate<>(userDao);
    long id = 1; //dbServiceUser.saveUser(new User(6, "dbServiceUser",34));
    JdbcTamplate<User> jdbcTamplate = new JdbcTamplate<>(userDao);
    jdbcTamplate.create(new User(6, "dbServiceUser",34));
    Optional<User> user = dbServiceUser.getUser(id);

/*    JdbcTamplate<User> jdbcTamplate = new JdbcTamplate<>(userDao);
    jdbcTamplate.create(new User(6, "dbServiceUser",34));
    User user = jdbcTamplate.load(1, User.class);*/

    System.out.println(user);
    user.ifPresentOrElse(
        crUser -> logger.info("created user, name:{}", crUser.getName()),
        () -> logger.info("user was not created")
    );
    User user1 = jdbcTamplate.load(1, User.class);
    System.out.println(user1);

  }

  private void createTable(DataSource dataSource) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement pst = connection.prepareStatement("create table User(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
      pst.executeUpdate();
    }
  }

}
