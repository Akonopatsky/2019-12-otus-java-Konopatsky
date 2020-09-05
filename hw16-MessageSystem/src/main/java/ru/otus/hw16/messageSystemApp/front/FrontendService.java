package ru.otus.hw16.messageSystemApp.front;

import ru.otus.hw16.dataaccsess.core.model.User;
import ru.otus.hw16.messageSystemApp.dto.UserData;
import ru.otus.hw16.messageSystemApp.dto.UserListData;
import ru.otus.hw16.messagesystem.client.MessageCallback;

public interface FrontendService {
    public void saveUser(User user, MessageCallback<UserData> dataConsumer);
    public void getAllUsers(MessageCallback<UserListData> dataConsumer);

}

