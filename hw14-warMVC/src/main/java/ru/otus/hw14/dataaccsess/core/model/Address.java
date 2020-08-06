package ru.otus.hw14.dataaccsess.core.model;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "address")
    private String street;

    public Address() {
    }

    public Address(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "street='" + street;
    }

}
