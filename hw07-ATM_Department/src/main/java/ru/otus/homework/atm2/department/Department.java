package ru.otus.homework.atm2.department;
import java.util.ArrayList;
import java.util.List;

public class Department {
    private final List<AtmForDepartment> atmList;
    private final AtmEventProducer control;
    public Department() {
        atmList = new ArrayList<>();
        control = new AtmEventProducer();
    }
    public Department addExistedAtm(AtmForDepartment atm) {
        atmList.add(atm);
        control.addListener(atm);
        return this;
    }
    public Department addNewAtm(int ... nominals) {
        AtmForDepartment.AtmProducer atmProducer = new AtmForDepartment.AtmProducer(nominals);
        AtmForDepartment atm = atmProducer.getNewAtm();
        this.addExistedAtm(atm);
        return this;
    }
    public int getAtmCount() {
        return atmList.size();
    }
    public AtmForDepartment getAtm(int atmnumber) {
        return atmList.get(atmnumber);
    }
    public void removeAtm(AtmForDepartment atm) {
        atmList.remove(atm);
        control.removeListener(atm);
    }
    public void restoreAllAtm() {
        control.restoreAllAtm();
    }
    public long getAmount() {
        return control.getAmount();
    }
}
