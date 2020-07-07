package ru.otus.homework.atm.firstatm;

import ru.otus.homework.atm.Banknote;
import ru.otus.homework.atm.PackOfBanknotes;
import ru.otus.homework.atm.secondatm.NewBanknote;

public class SimpleAtmDemo {
    public static void main(String[] args) throws Exception {
        SimpleAtm atm1 = new SimpleAtm();
        atm1.addCell(new SimpleCell(new SimpleBanknoteType(10)));
        atm1.addCell(new SimpleCell(new SimpleBanknoteType(50)));
        atm1.addCell(new SimpleCell(new SimpleBanknoteType(100)));
        atm1.addCell(new SimpleCell(new SimpleBanknoteType(500)));
        atm1.addCell(new SimpleCell(new SimpleBanknoteType(1000)));
        Banknote n10 = new SimpleBanknote(10);
        Banknote n50 = new SimpleBanknote(50);
        Banknote n100 = new NewBanknote(100, 810, 1230245645L);
        Banknote n200 = new SimpleBanknote(200);
        Banknote n500 = new SimpleBanknote(500);
        Banknote n1000 = new NewBanknote(1000, 810, 12135454534L);
        PackOfBanknotes pack = new SimplePack();
        pack.add(n10);
        pack.add(n50);
        pack.add(n100);
        pack.add(n200);
        pack.add(n1000);
        System.out.println("попытка внести пачку с номиналом 200 " + atm1.putPack(pack));
        System.out.println("денег стало " + atm1.getAmount());
        pack.clear();
        pack.add(n10);
        pack.add(n10);
        pack.add(n50);
        pack.add(n100);
        pack.add(n100);
        pack.add(n1000);
        atm1.putPack(pack);
        System.out.println("денег перед снятием разных типов купюр " + atm1.getAmount());
        PackOfBanknotes gettingPack = new SimplePack();
        System.out.println("получилось снять деньги " + atm1.getMoney(1100, gettingPack));
        System.out.println("после снятия " + atm1.getAmount());
        for (Banknote banknote : gettingPack) {
            if (banknote instanceof NewBanknote) {
                System.out.println("в пачке замечена купюра нового типа № " + ((NewBanknote) banknote).getNumber());
            }
        }
    }
}
