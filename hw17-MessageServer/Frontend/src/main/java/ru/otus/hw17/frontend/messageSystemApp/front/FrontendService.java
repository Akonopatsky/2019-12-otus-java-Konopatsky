package ru.otus.hw17.frontend.messageSystemApp.front;

import ru.otus.hw17.messagesystem.client.MessageCallback;
import ru.otus.hw17.msserver.dto.UserData;
import ru.otus.hw17.msserver.dto.UserListData;
import ru.otus.hw17.msserver.model.User;

public interface FrontendService {
    public void saveUser(User user, MessageCallback<UserData> dataConsumer);
    public void getAllUsers(MessageCallback<UserListData> dataConsumer);

}

