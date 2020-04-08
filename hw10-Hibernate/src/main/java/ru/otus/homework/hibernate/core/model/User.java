package ru.otus.homework.hibernate.core.model;

import javax.persistence.*;
import javax.persistence.Id;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public long id;

    @Column(name = "name")
    public String name;

    @Column(name = "age")
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

    public long getId() {
        return id;
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
        return (this.name.equals(that.name))&&(this.age == that.age) ;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
