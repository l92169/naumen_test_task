package ru.l92169.test_task.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;

    @Column(name = "count")
    private Integer count;

    @Column(name = "fromFile")
    private boolean fromFile;

    public User() {
    }

    public User(String name, Integer age, Integer count, boolean fromFile) {
        this.name = name;
        this.age = age;
        this.count = count;
        this.fromFile = fromFile;
    }
}
