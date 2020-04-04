package ru.otus.homework.jdbc.core.model;

import ru.otus.homework.jdbc.DIY.Id;

public class Account {
    public int no;
    public String type;
    @Id
    public int rest;

    public Account() {
    }

    public Account(Account account) {
        this.no = account.no;
        this.rest = account.rest;
        this.type = account.type;
    }


    public Account(int no, String type, int rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
