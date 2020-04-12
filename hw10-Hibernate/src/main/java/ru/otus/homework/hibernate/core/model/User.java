package ru.otus.homework.hibernate.core.model;

import javax.persistence.*;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Phone> phones = new ArrayList<>();

    public User() {
    }

    public User(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public User(long id, String name, int age, String street, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = new Address(street);
        this.phones = new ArrayList<>();
        this.addPhone(phone);
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        if (address!=null) return address.toString();
        return "unknown";
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String street) {
        this.address = new Address(street);
    }

    public void addPhone(String number) {
        this.phones.add(new Phone(this, number));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + getAddress() +
                ", phones=" + phones +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return (this.name.equals(that.name))
                &&(this.age == that.age)
                &&(this.address.toString().equals(that.address.toString()))
                &&(this.phones.toString().equals(that.phones.toString()));
    }
}
