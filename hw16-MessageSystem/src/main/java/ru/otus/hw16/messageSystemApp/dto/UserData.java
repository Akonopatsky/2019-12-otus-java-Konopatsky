package ru.otus.hw16.messageSystemApp.dto;

import ru.otus.hw16.dataaccsess.core.model.User;
import ru.otus.hw16.messagesystem.client.ResultDataType;

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
