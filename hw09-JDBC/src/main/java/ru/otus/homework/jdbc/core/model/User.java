package ru.otus.homework.jdbc.core.model;

import ru.otus.homework.jdbc.DIY.Id;

public class User {
    @Id
    public long id;
    public String name;
    public int age;

    public User() {
    }

    public User(User user) {
        this.id = user.id;
        this.name = user.name;
        this.age = user.age;
    }

    public User(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return this.name;
    }
}
