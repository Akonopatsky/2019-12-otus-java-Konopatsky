package ru.otus.homework.jdbc.core.model;

import java.math.BigDecimal;

public class Account {
    public long no;
    public String type;
    @Id
    public BigDecimal rest;

    public Account() {
    }

    public Account(Account account) {
        this.no = account.no;
        this.rest = account.rest;
        this.type = account.type;
    }

    public Account(long no, String type, BigDecimal rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account that = (Account) o;
        if (!this.type.equals(that.type)) return false;
        return this.rest.equals(that.rest);
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
