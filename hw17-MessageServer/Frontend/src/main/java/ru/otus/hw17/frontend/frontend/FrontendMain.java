package ru.otus.hw17.frontend.frontend;

public class FrontendMain {
    public static void main(String[] args) {
        FrontendSocketClient frontendSocketClient = new FrontendSocketClient();
        frontendSocketClient.start();
    }
}
