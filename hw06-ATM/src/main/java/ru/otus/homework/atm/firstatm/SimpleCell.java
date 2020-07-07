package ru.otus.homework.atm.firstatm;

import ru.otus.homework.atm.Banknote;
import ru.otus.homework.atm.BanknoteType;
import ru.otus.homework.atm.AtmCell;

import java.util.Stack;

public class SimpleCell implements AtmCell {
    final private Stack<Banknote> banknotes = new Stack<>();

    public SimpleCell(BanknoteType banknoteType) {
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
        if (banknote.getBanknoteType().equals(this.getBanknoteType())) {
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
        return banknotes.size();
    }

    @Override
    public BanknoteType getBanknoteType() {
        return banknoteType;
    }

}
