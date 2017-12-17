package com.epam.lab.ultimatewebservice.entity;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password_hash;
}
