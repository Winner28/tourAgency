package com.epam.lab.ultimatewebservice.entity;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
}

