package ru.otus.hw16.dataaccsess.core.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "addresses")
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

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
