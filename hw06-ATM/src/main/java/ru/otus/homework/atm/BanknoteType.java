package ru.otus.homework.atm;

public interface BanknoteType {
    long getNominal();

    boolean equals(BanknoteType o);
}
