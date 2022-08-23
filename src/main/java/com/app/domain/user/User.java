package com.app.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Getter
    @Setter
    @GeneratedValue
    private Long id;

    @Getter
    @Setter
    @Column(unique = true)
    private String login;

    @Getter
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }
}
