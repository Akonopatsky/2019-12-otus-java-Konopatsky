package ru.otus.homework.atm;

public interface AtmCell {
    boolean canPutInBanknote(Banknote banknote);
    boolean putInOneBanknote(Banknote banknote);
    Banknote getOutOneBanknote();
    long getCellNominal();
    int getQuantity();
    BanknoteType getBanknoteType();
}

