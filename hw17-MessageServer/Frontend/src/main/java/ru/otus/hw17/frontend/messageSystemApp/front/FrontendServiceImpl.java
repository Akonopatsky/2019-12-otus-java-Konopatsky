package ru.otus.hw17.frontend.messageSystemApp.front;

import ru.otus.hw17.messagesystem.client.MessageCallback;
import ru.otus.hw17.messagesystem.client.MsClient;
import ru.otus.hw17.messagesystem.message.Message;
import ru.otus.hw17.messagesystem.message.MessageType;
import ru.otus.hw17.msserver.dto.UserData;
import ru.otus.hw17.msserver.dto.UserListData;
import ru.otus.hw17.msserver.model.User;


public class FrontendServiceImpl implements FrontendService {

    private final MsClient msClient;
    private final String databaseServiceClientName;

    public FrontendServiceImpl(MsClient msClient, String databaseServiceClientName) {
        this.msClient = msClient;
        this.databaseServiceClientName = databaseServiceClientName;
    }

    @Override
    public void saveUser(User user, MessageCallback<UserData> dataConsumer) {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, new UserData(user),
                MessageType.SAVE_USER,dataConsumer);
        msClient.sendMessage(outMsg);
    }

    @Override
    public void getAllUsers(MessageCallback<UserListData> dataConsumer) {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, new UserListData(null),
                MessageType.GET_ALL_USER,dataConsumer);
        msClient.sendMessage(outMsg);
    }
}
