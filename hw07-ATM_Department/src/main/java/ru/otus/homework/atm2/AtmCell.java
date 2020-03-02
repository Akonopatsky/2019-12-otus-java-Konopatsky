package ru.otus.homework.atm2;

public interface AtmCell {
    boolean canPutInBanknote(Banknote banknote);
    boolean putInOneBanknote(Banknote banknote);
    Banknote getOutOneBanknote();
    long getCellNominal();
    int getQuantity();
    BanknoteType getBanknoteType();
    AtmCell createCopy();
}

