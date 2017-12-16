package com.epam.lab.ultimatewebservice.config;

import com.epam.lab.ultimatewebservice.db.dao.JdbcDAO;
import com.epam.lab.ultimatewebservice.db.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@ComponentScan({ "com.epam.lab.ultimatewebservice.service" })
public class SpringRootConfig {

    @Bean
    public UserDAO userDAO() {
        return new UserDAO();
    }

}