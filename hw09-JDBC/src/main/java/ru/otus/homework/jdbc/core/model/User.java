package ru.otus.homework.jdbc.core.model;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return (this.name.equals(that.name)) && (this.age == that.age);
    }

    public String getName() {
        return this.name;
    }
}
