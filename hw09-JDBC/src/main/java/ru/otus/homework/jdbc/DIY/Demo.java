package ru.otus.homework.jdbc.DIY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.jdbc.core.model.Account;
import ru.otus.homework.jdbc.core.model.User;
import ru.otus.homework.jdbc.core.sessionmanager.SessionManager;
import ru.otus.homework.jdbc.h2.DataSourceH2;
import ru.otus.homework.jdbc.jdbc.DbExecutor;
import ru.otus.homework.jdbc.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.homework.jdbc.mapper.JdbcMapper;
import ru.otus.homework.jdbc.mapper.Mapper;
import ru.otus.homework.jdbc.mapper.Tamplater;
import ru.otus.homework.jdbc.mapper.UnsupportedTypeException;


import javax.sql.DataSource;
import java.sql.*;

public class Demo {
  private static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
  private static Logger logger = LoggerFactory.getLogger(Demo.class);
  private final Connection connection;

  private Demo() throws SQLException {
    this.connection = DriverManager.getConnection(URL);
    this.connection.setAutoCommit(false);
  }

  public static void main(String[] args) throws SQLException, UnsupportedTypeException {
      Demo demo = new Demo();
      demo.createTable(new User());
      demo.createTable(new Account());
      demo.selectAllRecords("User");
      demo.selectAllRecords("Account");
      Tamplater tamplater1 = new Tamplater(new User());
      Tamplater tamplater2 = new Tamplater(new Account());

      DataSource dataSource = new DataSourceH2();
      SessionManager sessionManager = new SessionManagerJdbc(dataSource);
      DbExecutor<User> userDbExecutor = new DbExecutor<>();



//      Mapper<Account> accountMapper = new JdbcMapper<Account>();

  }

  private void createTable(User user) throws SQLException {
    try (PreparedStatement pst = connection.prepareStatement("create table User(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
      pst.executeUpdate();
    }
  }

 private void createTable(Account account) throws SQLException {
    try (PreparedStatement pst = connection.prepareStatement("create table Account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number )")) {
      pst.executeUpdate();
    }
  }
/*

  private void insertRecord(int id) throws SQLException {
    try (PreparedStatement pst = connection.prepareStatement("insert into test(id, name) values (?, ?)")) {
      Savepoint savePoint = this.connection.setSavepoint("savePointName");
      pst.setInt(1, id);
      pst.setString(2, "NameValue");
      try {
        int rowCount = pst.executeUpdate(); //Блокирующий вызов
        this.connection.commit();
        logger.info("inserted rowCount: {}", rowCount);
      } catch (SQLException ex) {
        this.connection.rollback(savePoint);
        logger.error(ex.getMessage(), ex);
      }
    }
  }
*/
  private void selectAllRecords(String tableName) throws SQLException {
    try (PreparedStatement pst = this.connection.prepareStatement("select * from " + tableName)) {
      try (ResultSet rs = pst.executeQuery()) {
        StringBuilder outString = new StringBuilder();
        if (rs.next()) {
          outString.append(rs.getString(1));
        }
        logger.info(outString.toString());
      }
    }
  }


  private void close() throws SQLException {
    this.connection.close();
  }

}
