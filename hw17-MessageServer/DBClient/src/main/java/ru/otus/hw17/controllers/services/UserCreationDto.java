package ru.otus.hw17.controllers.services;

import ru.otus.hw17.dataaccsess.core.model.User;

public class UserCreationDto {

    private String name = "";
    private int age;
    private String address = "";
    private String phone = "";

    public UserCreationDto() {
        name = "blank Name";
        age = 0;
        address = "blank address";
        phone = "+00000000000";
    }

    public UserCreationDto(String name, int age, String address, String phone) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
    }

    public User createUser() {
        User user = new User(name, age, address);
        user.addPhone(phone);
        return user;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserCreationDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
