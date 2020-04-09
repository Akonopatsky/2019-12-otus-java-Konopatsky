package ru.otus.homework.hibernate.core.model;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class AddressDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public long id;
    private String street;
}
