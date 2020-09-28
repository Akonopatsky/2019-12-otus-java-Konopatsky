package ru.otus.hw17.dataaccsess.core.model;

import java.io.Serializable;

public class Phone implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;

    private User user;

    private String number;

    public Phone() {
    }

    public Phone(User user, String number) {
        this.user = user;
        this.number = number;
    }


    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "phone{" +
                "number='" + number + '\'' +
                '}';
    }
}
