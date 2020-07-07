package ru.otus.homework.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.atm.firstatm.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AtmTest {
    private Atm atm1 = new SimpleAtm();
    private Banknote s10 = new SimpleBanknote(10);
    private Banknote s50 = new SimpleBanknote(50);
    private Banknote s100 = new SimpleBanknote(100);
    private Banknote s500 = new SimpleBanknote(500);
    private Banknote s1000 = new SimpleBanknote(1000);

    @BeforeEach
    void setUp() {
        atm1.removeAllCells();
        atm1.addCell(new SimpleCell(new SimpleBanknoteType(10)));
        atm1.addCell(new SimpleCell(new SimpleBanknoteType(50)));
        atm1.addCell(new SimpleCell(new SimpleBanknoteType(100)));
        atm1.addCell(new SimpleCell(new SimpleBanknoteType(500)));
        atm1.addCell(new SimpleCell(new SimpleBanknoteType(1000)));
        PackOfBanknotes pack = new SimplePack();
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
    void addCell() throws UnsupportedBanknoteException {
        PackOfBanknotes pack1 = new SimplePack();
        pack1.add(new SimpleBanknote(200));
        assertFalse(atm1.putPack(pack1));
        atm1.addCell(new SimpleCell(new SimpleBanknoteType(200)));
        assertTrue(atm1.putPack(pack1));
    }

    @Test
    void getMoney() {
        PackOfBanknotes pack1 = new SimplePack();
        atm1.getMoney(600, pack1);
        assertEquals(1060, atm1.getAmount());
        List<Banknote> pack2 = new ArrayList<>();
        pack2.add(s500);
        pack2.add(s100);
        assertEquals(pack1, pack2);
    }

    @Test
    void putPack() {
        PackOfBanknotes pack = new SimplePack();
        pack.add(s100);
        pack.add(s1000);
        try {
            atm1.putPack(pack);
        } catch (UnsupportedBanknoteException e) {
            e.printStackTrace();
        }
        assertEquals(atm1.getAmount(), 2760);

    }

    @Test
    void getAmount() {
        assertEquals(1660, atm1.getAmount());
    }

    @Test
    void removeAllCells() {
        atm1.removeAllCells();
        PackOfBanknotes pack = new SimplePack();
        pack.add(s10);
        assertEquals(0, atm1.getAmount());
    }
}