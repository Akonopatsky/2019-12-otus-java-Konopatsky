package ru.otus.homework.atm;
public interface Atm {
    void addCell(AtmCell cell);
    void removeAllCells();
    boolean getMoney(long amount, PackOfBanknotes pack);
    boolean putPack(PackOfBanknotes pack) throws UnsupportedBanknoteException;
    long getAmount();
}
