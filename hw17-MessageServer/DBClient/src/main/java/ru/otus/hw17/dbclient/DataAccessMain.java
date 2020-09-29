package ru.otus.hw17.dbclient;

public class DataAccessMain {
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";
    private static final int PORT = 8090;
    private static final String HOST = "localhost";
    public static void main(String[] args) {
        DataAccessSystem dataAccessSystem = new DataAccessSystem(DATABASE_SERVICE_CLIENT_NAME, PORT, HOST);
        dataAccessSystem.start();
    }
}
