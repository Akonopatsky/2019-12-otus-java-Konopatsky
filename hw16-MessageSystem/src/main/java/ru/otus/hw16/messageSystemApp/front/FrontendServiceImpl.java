package ru.otus.hw16.messageSystemApp.front;

import ru.otus.hw16.messageSystemApp.dto.UserData;
import ru.otus.hw16.messagesystem.client.MessageCallback;
import ru.otus.hw16.messagesystem.client.MsClient;
import ru.otus.hw16.messagesystem.message.Message;
import ru.otus.hw16.messagesystem.message.MessageType;


public class FrontendServiceImpl implements FrontendService {

    private final MsClient msClient;
    private final String databaseServiceClientName;

    public FrontendServiceImpl(MsClient msClient, String databaseServiceClientName) {
        this.msClient = msClient;
        this.databaseServiceClientName = databaseServiceClientName;
    }

    @Override
    public void getUserData(long userId, MessageCallback<UserData> dataConsumer) {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, new UserData(userId),
                MessageType.USER_DATA, dataConsumer);
        msClient.sendMessage(outMsg);
    }
}
