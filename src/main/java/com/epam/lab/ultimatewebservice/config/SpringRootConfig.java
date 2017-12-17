package com.epam.lab.ultimatewebservice.config;

import com.epam.lab.ultimatewebservice.db.connpool.ConnectionPool;
import com.epam.lab.ultimatewebservice.db.dao.JdbcDAO;
import com.epam.lab.ultimatewebservice.db.dao.UserDAO;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@ComponentScan({ "com.epam.lab.ultimatewebservice.service" })
public class SpringRootConfig {

    @Bean
    @SneakyThrows
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("util.properties"));
        dataSource.setDriverClassName(properties.getProperty("jdbc.mysql.driverClass"));
        dataSource.setUrl(properties.getProperty("jdbc.mysql.url"));
        dataSource.setUsername(properties.getProperty("jdbc.mysql.root"));
        dataSource.setPassword(properties.getProperty("jdbc.mysql.password"));
        return dataSource;
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAO();
    }

    @Bean
    public ConnectionPool getConnectionPool() {
        return ConnectionPool.getInstance();
    }

}