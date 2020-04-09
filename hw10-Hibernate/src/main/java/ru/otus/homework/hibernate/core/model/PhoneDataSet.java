package ru.otus.homework.hibernate.core.model;

import javax.persistence.*;

@Entity
@Table(name = "phones")
public class PhoneDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public long id;
    private String number;
}
