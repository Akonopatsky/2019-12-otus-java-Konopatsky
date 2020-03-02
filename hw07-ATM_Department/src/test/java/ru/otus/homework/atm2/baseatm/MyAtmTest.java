package ru.otus.homework.atm2.baseatm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.atm2.*;

import static org.junit.jupiter.api.Assertions.*;

class MyAtmTest {
    private MyAtm atm1 = new MyAtm();
    private  Banknote s10 = new SimpleBanknote(10);
    private  Banknote s50 = new SimpleBanknote(50);
    private  Banknote s100 = new SimpleBanknote(100);
    private  Banknote s500 = new SimpleBanknote(500);
    private  Banknote s1000 = new SimpleBanknote(1000);
    @BeforeEach
    void setUp() {
        atm1.removeAllCells();
        atm1.addCell(new MyCell(new MyBanknoteType(10)));
        atm1.addCell(new MyCell(new MyBanknoteType(50)));
        atm1.addCell(new MyCell(new MyBanknoteType(100)));
        atm1.addCell(new MyCell(new MyBanknoteType(500)));
        atm1.addCell(new MyCell(new MyBanknoteType(1000)));
        PackOfBanknotes pack = new MyPack();
        pack.add(s10);
        pack.add(s50);
        pack.add(s100);
        pack.add(s500);
        pack.add(s1000);
        try {
            atm1.putPack(pack);
        } catch (UnsupportedBanknoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createCopy() {
        MyAtm copyOfAtm1 = atm1.createCopy();
        assertEquals(copyOfAtm1.getAmount(),atm1.getAmount());
        long oldAmount = atm1.getAmount();
        MyPack pack = new MyPack();
        assertTrue(atm1.getMoney(1000, pack));
        long atm1Amount = atm1.getAmount();
        assertEquals(copyOfAtm1.getAmount(),oldAmount);
        assertTrue(copyOfAtm1.getMoney(1500,pack));
        assertEquals(atm1.getAmount(),atm1Amount);
        copyOfAtm1.removeAllCells();
        assertEquals(atm1.getAmount(),atm1Amount);
        assertEquals(2500, pack.getAmount());
    }
}