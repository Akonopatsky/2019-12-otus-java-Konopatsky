package ru.otus.hw17.messageSystemApp.front;

import ru.otus.hw17.dataaccsess.core.model.User;
import ru.otus.hw17.messageSystemApp.dto.UserData;
import ru.otus.hw17.messageSystemApp.dto.UserListData;
import ru.otus.hw17.messagesystem.client.MessageCallback;

public interface FrontendService {
    public void saveUser(User user, MessageCallback<UserData> dataConsumer);
    public void getAllUsers(MessageCallback<UserListData> dataConsumer);

}

