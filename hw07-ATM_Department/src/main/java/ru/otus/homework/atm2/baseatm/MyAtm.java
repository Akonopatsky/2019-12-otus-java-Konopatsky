package ru.otus.homework.atm2.baseatm;
import ru.otus.homework.atm2.*;
import java.util.ArrayList;
import java.util.Comparator;

public class MyAtm implements BankOperations, ClientOperations {
    private ArrayList<AtmCell> cells;
    @Override
    public boolean getMoney(long amount, PackOfBanknotes pack) {
        if(!haveEnoughBanknotes(amount)) return false;
        long ost = amount;
        int i = cells.size()-1;
        AtmCell currentCell;
        while (i>=0) {
            if (ost == 0) return true;
            currentCell = cells.get(i);
            if (currentCell.getQuantity()>0 && ost >= currentCell.getCellNominal()) {
                pack.add(currentCell.getOutOneBanknote());
                ost-=currentCell.getCellNominal();
            }
            else {
                i--;
            }
        }
        return false;
    }
    private boolean haveEnoughBanknotes(long amount) {
        long ost = amount;
        int[] tempCells = new int[cells.size()];
        for (int i = 0; i < cells.size(); i++) {
            tempCells[i] = cells.get(i).getQuantity();
        }
        int i = tempCells.length-1;
        long nominal;
        while (i>=0) {
            if (ost == 0) return true;
            nominal = cells.get(i).getCellNominal();
            if (tempCells[i]>0 && ost >= nominal) {
                tempCells[i]--;
                ost-=nominal;
            }
            else {
                i--;
            }
        }
        return false;
    }
    @Override
    public boolean putPack(PackOfBanknotes pack) throws UnsupportedBanknoteException {
        if (!canPutPack(pack)) return false;
        for (Banknote banknote : pack) {
             putBanknoteIn(banknote);
        }
        return true;
    }
    private void putBanknoteIn(Banknote banknote) throws UnsupportedBanknoteException {
        for (int i = 0; i < cells.size() ; i++) {
            AtmCell cell = cells.get(i);
            if (banknote.getBanknoteType().equals(cell.getBanknoteType())) {
                cell.putInOneBanknote(banknote);
                return;
            }
        }
        throw new UnsupportedBanknoteException();
    }
    @Override
    public long getAmount() {
        long amount = 0;
        for (AtmCell cell : cells) {
            amount+=cell.getCellNominal()*cell.getQuantity();
        }
        return amount;
    }

    @Override
    public void removeAllCells() {
        this.cells.clear();
    }

    public MyAtm() {
        this.cells = new ArrayList<>();
    }
    public void addCell(AtmCell cell) {
        cells.add(cell);
        cells.sort(Comparator.comparing(AtmCell::getCellNominal));
    }
    private boolean canPutPack(PackOfBanknotes pack){
        for (Banknote banknote : pack) {
            if (!canPutBanknote(banknote)) return false;
        }
        return true;
    }
    private boolean canPutBanknote(Banknote banknote){
        for (AtmCell cell : cells) {
            if (cell.canPutInBanknote(banknote)) return true;
        }
        return false;
    }
    public  MyAtm createCopy () {
        MyAtm result = new MyAtm();
        for (AtmCell cell : this.cells) {
            result.cells.add(cell.createCopy());
        }
        return result;
    }
}
