package ru.otus.homework.atm.firstatm;

import ru.otus.homework.atm.Banknote;
import ru.otus.homework.atm.BanknoteType;

public class SimpleBanknote implements Banknote {
    final private BanknoteType banknoteType;

    public SimpleBanknote(int nominal) {
        this.banknoteType = new SimpleBanknoteType(nominal);
    }

    @Override
    public BanknoteType getBanknoteType() {
        return banknoteType;
    }
}
