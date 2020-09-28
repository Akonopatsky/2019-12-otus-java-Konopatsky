package ru.otus.hw17.frontend.messageSystemApp.front.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw17.messagesystem.RequestHandler;
import ru.otus.hw17.messagesystem.client.CallbackRegistry;
import ru.otus.hw17.messagesystem.client.MessageCallback;
import ru.otus.hw17.messagesystem.client.ResultDataType;
import ru.otus.hw17.messagesystem.message.Message;
import ru.otus.hw17.messagesystem.message.MessageHelper;
import ru.otus.hw17.msserver.dto.UserData;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class GetUserDataResponseHandler implements RequestHandler<UserData> {
    private static final Logger logger = LoggerFactory.getLogger(GetUserDataResponseHandler.class);

    private final CallbackRegistry callbackRegistry;

    public GetUserDataResponseHandler(CallbackRegistry callbackRegistry) {
        this.callbackRegistry = callbackRegistry;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        logger.info("new message:{}", msg);
        try {
            MessageCallback<? extends ResultDataType> callback = callbackRegistry.getAndRemove(msg.getCallbackId());
            if (callback != null) {
                try (FileOutputStream fout = new FileOutputStream("f222.txt")) {
                    fout.write(msg.getPayload());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                callback.accept(MessageHelper.getPayload(msg));
            } else {
                logger.error("callback for Id:{} not found", msg.getCallbackId());
            }
        } catch (Exception ex) {
            logger.error("msg:{}", msg, ex);
        }
        return Optional.empty();
    }
}
