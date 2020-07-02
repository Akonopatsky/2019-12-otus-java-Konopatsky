package ru.otus.hw12.dataaccsess.core.model;


import javax.persistence.*;

@Entity
@Table(name = "phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "phone")
    private String number;

    public Phone() {
    }

    public Phone(User user, String number) {
        this.user = user;
        this.number = number;
    }

    @Override
    public String toString() {
        return "phone{" +
                "number='" + number + '\'' +
                '}';
    }
}
