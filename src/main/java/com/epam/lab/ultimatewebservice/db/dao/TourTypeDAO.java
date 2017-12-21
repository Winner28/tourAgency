package com.epam.lab.ultimatewebservice.db.dao;

import com.epam.lab.ultimatewebservice.db.connpool.ConnectionPool;
import com.epam.lab.ultimatewebservice.entity.TourType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TourTypeDAO {

    private final static String ADD_TOUR =
            "INSERT INTO tour_types (tour_type) VALUES(?)";
    private final static String DELETE_TOUR =
            "DELETE FROM tour_types WHERE id=?";
    private final static String GET_ALL_TOURS =
            "SELECT id, tour_type FROM tour_types";
    private final static String GET_TOUR_BY_ID =
            "SELECT id, tour_type FROM tour_types WHERE id=?";
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

    public Optional<TourType> getTourTypeById(int id) {
        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new TourType()
                            .setId(resultSet.getInt(ID))
                            .setTourType(resultSet.getString(TOUR_TYPE));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        },GET_TOUR_BY_ID, id));

    }

    public Optional<TourType> addTourType(String tourType) {
        return Optional.ofNullable(jdbcDAO.mapPreparedStatementFlagged(preparedStatement -> {
            try (ResultSet rs = preparedStatement.getGeneratedKeys()){
                return !rs.next() ? null : new TourType()
                                    .setId(rs.getInt(ID))
                                    .setTourType(TOUR_TYPE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }, ADD_TOUR, tourType));
    }

    public boolean deleteTourType(int id) {
        return jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try {
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }, DELETE_TOUR, id);
    }

    public boolean updateTourType(TourType tourType) {
        return jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try {
                 preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        },UPDATE_TOUR_BY_ID, tourType.getTourType(), tourType.getId());
    }

    public List<TourType> getAllTours() {
        List<TourType> tourTypeList= new ArrayList<>();
        jdbcDAO.withResultSet(rs -> {
            try {
                while (rs.next()) {
                    tourTypeList.add(new TourType()
                            .setId(rs.getInt(ID))
                            .setTourType(rs.getString(TOUR_TYPE)));
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        },GET_ALL_TOURS);
        return tourTypeList;
    }
}
