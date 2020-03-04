package ru.otus.homework.atm2;

public interface BankOperations {
    void addCell(AtmCell cell);
    void removeAllCells();
    long getAmount();
}
