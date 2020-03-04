package ru.otus.homework.atm2.baseatm;

import ru.otus.homework.atm2.AtmCell;
import ru.otus.homework.atm2.Banknote;
import ru.otus.homework.atm2.BanknoteType;
import java.util.ArrayDeque;
import java.util.Deque;

public class MyCell implements AtmCell {
    final private Deque<Banknote> banknotes = new ArrayDeque<>();
    final private BanknoteType banknoteType;
    public MyCell(BanknoteType banknoteType) {
        this.banknoteType = banknoteType;
    }
    @Override
    public Banknote getOutOneBanknote() {
        return banknotes.pop();
    }

    @Override
    public boolean canPutInBanknote(Banknote banknote) {
        return getBanknoteType().equals(banknote.getBanknoteType());
    }
    @Override
    public boolean putInOneBanknote(Banknote banknote) {
        if (banknote.getBanknoteType().equals(this.getBanknoteType()))
        {
            banknotes.push(banknote);
            return true;
        }
        return false;
    }
    @Override
    public long getCellNominal() {
        return this.getBanknoteType().getNominal();
    }
    @Override
    public int getQuantity() {
        return  banknotes.size();
    }
    @Override
    public BanknoteType getBanknoteType() {
        return banknoteType;
    }

    public MyCell createCopy() {
        MyCell result = new MyCell(this.getBanknoteType());
        result.banknotes.addAll(banknotes);
        return result;
    }
}
