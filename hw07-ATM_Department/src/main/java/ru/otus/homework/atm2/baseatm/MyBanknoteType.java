package ru.otus.homework.atm2.baseatm;

import ru.otus.homework.atm2.BanknoteType;

public class MyBanknoteType implements BanknoteType {
    final private int nominal;
    public MyBanknoteType(int nominal) {
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
    public static MyBanknoteType create(int nominal) {
        return  new MyBanknoteType(nominal);
    }
}
