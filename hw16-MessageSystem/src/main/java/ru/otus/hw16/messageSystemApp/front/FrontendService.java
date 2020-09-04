package ru.otus.hw16.messageSystemApp.front;

import ru.otus.hw16.messageSystemApp.dto.UserData;
import ru.otus.hw16.messagesystem.client.MessageCallback;

public interface FrontendService {
    void getUserData(long userId, MessageCallback<UserData> dataConsumer);
}

