package ru.otus.homework.atm2.department;

import ru.otus.homework.atm2.PackOfBanknotes;
import ru.otus.homework.atm2.UnsupportedBanknoteException;
import ru.otus.homework.atm2.baseatm.MyAtm;
import ru.otus.homework.atm2.baseatm.MyBanknoteType;
import ru.otus.homework.atm2.baseatm.MyCell;
import java.util.ArrayList;
import java.util.List;

public class AtmForDepartment implements AtmListener {
    private MyAtm atm;
    private MyAtm savedState;
    private AtmForDepartment(MyAtm atm) {
        this.atm = atm;
        saveAtmState();
    }
    void saveAtmState() {
        this.savedState = atm.createCopy();
    }
    void restoreAtmState() {
        this.atm = savedState.createCopy();
    }
    AtmForDepartment addCell(MyCell cell) {
        atm.addCell(cell);
        return this;
    }
    AtmForDepartment removeAllCells() {
        atm.removeAllCells();
        return this;
    }
    @Override
    public void restoreInitState() {
        restoreAtmState();
    }
    @Override
    public long getAmount() {
        return atm.getAmount();
    }
    public boolean putPack(PackOfBanknotes pack)  {
        try {
            return atm.putPack(pack);
        } catch (UnsupportedBanknoteException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean getMoney(long amount, PackOfBanknotes pack) {
        return atm.getMoney(amount, pack);
    }
    public static class AtmProducer {
        final private List<MyCell> atmCells = new ArrayList<>();
        public AtmProducer(int ... nominals) {
            for (int nominal : nominals) {
                atmCells.add(new MyCell(new MyBanknoteType(nominal)));
            }
        }
        public AtmProducer addCell(int nominal) {
            atmCells.add(new MyCell(new MyBanknoteType(nominal)));
            return this;
        }
        public AtmForDepartment getNewAtm() {
            AtmForDepartment result = new AtmForDepartment(new MyAtm());
            for (MyCell atmCell : atmCells) {
                result.atm.addCell(atmCell.createCopy());
            }
            result.saveAtmState();
            return result;
        }
    }

}
