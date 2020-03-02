package ru.otus.homework.atm2.baseatm;

import ru.otus.homework.atm2.AtmCell;
import ru.otus.homework.atm2.Banknote;
import ru.otus.homework.atm2.BanknoteType;
import java.util.ArrayDeque;
import java.util.Deque;

public class MyCell implements AtmCell {
    final private Deque<Banknote> banknotes = new ArrayDeque<>();
    public MyCell(BanknoteType banknoteType) {
        this.banknoteType = banknoteType;
    }
    final private BanknoteType banknoteType;
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
    // тут копия через инстанс
    public MyCell createCopy() {
        MyCell result = new MyCell(this.getBanknoteType());
/*        for (Banknote banknote : banknotes) {
            result.banknotes.add(banknote);
        }*/
        result.banknotes.addAll(banknotes);
        return result;
    }
}
