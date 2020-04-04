package ru.otus.homework.jdbc.h2;

import ru.otus.homework.jdbc.core.model.Account;
import ru.otus.homework.jdbc.core.model.User;

import javax.sql.DataSource;
import java.sql.*;

public class TableFactory {
    private final DataSource dataSource;

    public TableFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createTable(User user) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table user(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
    }

    public void createTable(Account account) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number )")) {
            pst.executeUpdate();
        }
    }
}
