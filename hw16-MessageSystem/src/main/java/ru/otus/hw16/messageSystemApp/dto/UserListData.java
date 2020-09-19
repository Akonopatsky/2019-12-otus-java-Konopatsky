package ru.otus.hw16.messageSystemApp.dto;

import ru.otus.hw16.dataaccsess.core.model.User;
import ru.otus.hw16.messagesystem.client.ResultDataType;

import java.util.List;

public class UserListData extends ResultDataType {
    private final List<User> data;

    public UserListData(List<User> data) {
        this.data = data;
    }

    public List<User> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "UserListData{" +
                "data=" + data +
                '}';
    }
}
