package com.epam.lab.ultimatewebservice.entity;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String password_hash;
}

