package ru.otus.homework.jdbc.core.model;

import ru.otus.homework.jdbc.mapper.Id;

public class Account {
    public int no;
    public String type;
    @Id
    public int number;

    public Account() {
    }

    public Account(int no, String type, int number) {
        this.no = no;
        this.type = type;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", number=" + number +
                '}';
    }
}
