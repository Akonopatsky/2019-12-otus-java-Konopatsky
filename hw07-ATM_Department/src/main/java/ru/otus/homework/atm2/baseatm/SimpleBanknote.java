package ru.otus.homework.atm2.baseatm;

import ru.otus.homework.atm2.Banknote;
import ru.otus.homework.atm2.BanknoteType;

public class SimpleBanknote implements Banknote {
    final private BanknoteType banknoteType;

    public SimpleBanknote(int nominal) {
        this.banknoteType = new MyBanknoteType(nominal);
    }
    @Override
    public BanknoteType getBanknoteType() {
        return banknoteType;
    }
}
