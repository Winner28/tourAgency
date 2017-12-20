package com.epam.lab.ultimatewebservice.db.dao;

import com.epam.lab.ultimatewebservice.db.connpool.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TourTypeDAO {

    private final static String ADD_TOUR =
            "INSERT INTO Tour_Types(id, tour_type) VALUES(?,?)";
    private final static String DELETE_TOUR =
            "DELETE FROM Tour_Types WHERE id=?";
    private final static String GET_ALL_TOURS =
            "SELECT id, tour_type FROM Tour_Types";
    private final static String GET_TOUR_BY_ID =
            "SELECT id, tour_type FROM Tour_Types WHERE id=?";
    private final static String UPDATE_TOUR_BY_ID =
            "UPDATE Tour_Types SET tour_type=? WHERE id=?";
    private final static String ID = "id";
    private final static String TOUR_TYPE= "tour_type";

    private JdbcDAO jdbcDAO;

    @Autowired
    public TourTypeDAO(ConnectionPool connectionPool) {
        jdbcDAO = () -> {
            try {
                return connectionPool.getConnection();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        };
    }
}
