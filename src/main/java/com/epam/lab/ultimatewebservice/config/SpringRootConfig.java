package com.epam.lab.ultimatewebservice.config;

import com.epam.lab.ultimatewebservice.db.dao.JdbcDAO;
import com.epam.lab.ultimatewebservice.db.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@ComponentScan({ "com.epam.lab.ultimatewebservice.service" })
public class SpringRootConfig {

    @Bean
    public DataSource getDataSource() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        return (DataSource) context.getBean("dataSource");
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAO(getDataSource());
    }

}