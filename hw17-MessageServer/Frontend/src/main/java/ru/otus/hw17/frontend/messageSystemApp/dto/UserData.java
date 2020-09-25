package ru.otus.hw17.frontend.messageSystemApp.dto;

import ru.otus.hw17.frontend.messageSystemApp.model.User;
import ru.otus.hw17.frontend.messagesystem.client.ResultDataType;

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
