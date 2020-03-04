package ru.otus.homework.atm2;
public interface ClientOperations {
    boolean getMoney(long amount, PackOfBanknotes pack);
    boolean putPack(PackOfBanknotes pack) throws UnsupportedBanknoteException;
}
