package ru.otus.hw16.services;

import ru.otus.hw16.dataaccsess.core.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserCreationDto {

    private String name = "";
    private int age;
    private String address = "";
    private List<String> phones;

    public UserCreationDto() {
        name = "Иванов Иван";
        age = 25;
        address = "ул. Луначарского";
        phones = new ArrayList<>();
        addBlankPhone();
    }

    public User createUser() {
        User user = new User(name, age, address);
        phones.stream().forEach(user::addPhone);
        return user;
    }

    public void addBlankPhone() {
        this.phones.add("0000000");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }
}
