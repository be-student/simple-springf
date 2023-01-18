package com.example.testing.domain.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;

    private String email;
    private String pwd;
    private String role;

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
