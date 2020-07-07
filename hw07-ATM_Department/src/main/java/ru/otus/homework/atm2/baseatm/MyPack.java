package ru.otus.homework.atm2.baseatm;

import ru.otus.homework.atm2.Banknote;
import ru.otus.homework.atm2.BanknoteType;
import ru.otus.homework.atm2.PackOfBanknotes;

import java.util.ArrayList;

public class MyPack extends ArrayList<Banknote> implements PackOfBanknotes {
    public MyPack addBanknotes(int... nominals) {
        for (int nominal : nominals) {
            add(new SimpleBanknote(nominal));
        }
        return this;
    }

    public long getAmount() {
        long sum = 0;
        for (Banknote banknote : this) {
            sum += banknote.getBanknoteType().getNominal();
        }
        return sum;
    }
}

