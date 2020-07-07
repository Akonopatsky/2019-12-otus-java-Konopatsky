package ru.otus.homework.atm2.department;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.atm2.baseatm.MyPack;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {
    private Department department;
    private MyPack pack150 = new MyPack().addBanknotes(50, 100);
    private MyPack pack600 = new MyPack().addBanknotes(100, 500);
    private MyPack pack3000 = new MyPack().addBanknotes(1000, 2000);
    private MyPack pack7000 = new MyPack().addBanknotes(2000, 5000);
    private AtmForDepartment.AtmProducer produceSmallAtm = new AtmForDepartment.AtmProducer(50, 100, 500);
    private AtmForDepartment.AtmProducer produceBigAtm = new AtmForDepartment.AtmProducer().addCell(1000).addCell(2000).addCell(5000);
    private AtmForDepartment atm1;
    private AtmForDepartment atm2;
    private AtmForDepartment atm3;
    private AtmForDepartment atm4;

    @BeforeEach
    void setUp() {
        department = new Department();
        atm1 = produceSmallAtm.getNewAtm();
        atm2 = produceSmallAtm.getNewAtm();
        atm3 = produceBigAtm.getNewAtm();
        atm4 = produceBigAtm.getNewAtm();
        atm1.putPack(pack150);
        atm2.putPack(pack600);
        atm3.putPack(pack3000);
        atm4.putPack(pack7000);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void addExistedAtm() {
        assertEquals(0, department.getAtmCount());
        department
                .addExistedAtm(atm1)
                .addExistedAtm(atm2);
        assertEquals(2, department.getAtmCount());
    }

    @Test
    void addNewAtm() {
        assertEquals(0, department.getAtmCount());
        department
                .addNewAtm(100, 200, 500)
                .addNewAtm(100, 200, 500)
                .addNewAtm(100, 200, 500);
        assertEquals(3, department.getAtmCount());
    }

    @Test
    void getAtmCount() {

    }

    @Test
    void getAtm() {
        department
                .addExistedAtm(atm1)
                .addExistedAtm(atm2)
                .addExistedAtm(atm3)
                .addExistedAtm(atm4);
        assertEquals(600, department.getAtm(1).getAmount());
    }

    @Test
    void removeAtm() {
        department
                .addExistedAtm(atm1)
                .addExistedAtm(atm2)
                .addExistedAtm(atm3)
                .addExistedAtm(atm4)
                .addNewAtm(50, 100, 500, 1000, 2000, 5000);

        department.getAtm(4).putPack(pack150);
        department.getAtm(4).putPack(pack7000);
        department.removeAtm(atm2);
        assertEquals(17300, department.getAmount());
        assertEquals(4, department.getAtmCount());

    }

    @Test
    void restoreAllAtm() {
        department
                .addExistedAtm(atm1)
                .addExistedAtm(atm2)
                .addExistedAtm(atm3)
                .addExistedAtm(atm4)
                .addNewAtm(50, 100, 500, 1000, 2000, 5000);

        department.getAtm(4).putPack(pack150);
        department.getAtm(4).putPack(pack7000);
        atm3.saveAtmState();
        department.restoreAllAtm();
        assertEquals(3000, department.getAmount());
    }

    @Test
    void getAmount() {
        department
                .addExistedAtm(atm1)
                .addExistedAtm(atm2)
                .addExistedAtm(atm3)
                .addExistedAtm(atm4)
                .addNewAtm(50, 100, 500, 1000, 2000, 5000);

        department.getAtm(4).putPack(pack150);
        department.getAtm(4).putPack(pack7000);
        assertEquals(17900, department.getAmount());
    }
}