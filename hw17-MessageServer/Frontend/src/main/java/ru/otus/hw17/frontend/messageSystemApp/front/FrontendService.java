package ru.otus.hw17.frontend.messageSystemApp.front;

import ru.otus.hw17.frontend.messageSystemApp.dto.UserData;
import ru.otus.hw17.frontend.messageSystemApp.dto.UserListData;
import ru.otus.hw17.frontend.messageSystemApp.model.User;
import ru.otus.hw17.messagesystem.client.MessageCallback;

public interface FrontendService {
    public void saveUser(User user, MessageCallback<UserData> dataConsumer);
    public void getAllUsers(MessageCallback<UserListData> dataConsumer);

}

