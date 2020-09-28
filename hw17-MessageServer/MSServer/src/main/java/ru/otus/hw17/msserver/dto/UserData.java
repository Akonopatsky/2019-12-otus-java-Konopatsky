package ru.otus.hw17.msserver.dto;


import ru.otus.hw17.messagesystem.client.ResultDataType;
import ru.otus.hw17.msserver.model.User;

public class UserData extends ResultDataType {
    private final User data;

    public UserData(User data) {
        this.data = data;
    }

    public User getData() {
        return data;
    }

    @Override
    public String toString() {
        return "UserData{" +
                ", data='" + data + '\'' +
                '}';
    }
}
