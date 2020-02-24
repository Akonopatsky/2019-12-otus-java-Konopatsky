package ru.otus.homework.atm.firstatm;

import ru.otus.homework.atm.BanknoteType;

public class SimpleBanknoteType implements BanknoteType {
    final private int nominal;
    public SimpleBanknoteType(int nominal) {
        this.nominal = nominal;
    }
    @Override
    public long getNominal() {
        return nominal;
    }
    @Override
    public boolean equals(BanknoteType o) {
        return this.getNominal()==o.getNominal();
    }
}
