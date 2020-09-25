package ru.otus.hw17.messagesystem.message;

public enum MessageType {
    SAVE_USER ("SaveUser"),
    GET_ALL_USER ("GetAllUsers");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
