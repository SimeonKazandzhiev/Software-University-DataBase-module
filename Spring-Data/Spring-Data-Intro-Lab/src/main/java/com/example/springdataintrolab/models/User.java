package com.example.springdataintrolab.models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private Integer age;
    @OneToMany(mappedBy = "user")
    private Set<Account> account;

    public User() {
        this.account = new HashSet<>();
    }

    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Column
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Account> getAccount() {
        return account;
    }

    public void setAccount(Set<Account> account) {
        this.account = account;
    }
}
