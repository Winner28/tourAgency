package com.epam.lab.ultimatewebservice.db.dao;

import com.epam.lab.ultimatewebservice.db.connpool.ConnectionPool;
import com.epam.lab.ultimatewebservice.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class OrderDAO {

    private static final String ADD_ORDER =
            "INSERT INTO Orders(date, active, tourId, userId) VALUES(?,?,?,?)";
    private static final String GET_ORDER_BY_ID =
            "SELECT (date, active, tourId, userId) FROM Orders WHERE id=?";
    private static final String GET_ORDER_BY_TOUR_ID =
            "SELECT (id, date, active, tourId, userId) FROM Orders WHERE tourId=?";
    private static final String GET_ORDER_BY_USER_ID =
            "SELECT (id, date, active, tourId, userId) FROM Orders WHERE userId=?";
    private static final String DELETE_ORDER_BY_ID =
            "DELETE FROM Orders WHERE id=?";
    private static final String UPDATE_ORDER = "UPDATE Orders SET date=?, active=?, tourId=?, userId=? WHERE id=?";

    private static final String ID = "id";
    private static final String DATE = "date";
    private static final String ACTIVE = "active";
    private static final String TOUR_ID = "tourId";
    private static final String USER_ID = "userId";

    private JdbcDAO jdbcDAO;

    @Autowired
    public OrderDAO(ConnectionPool connectionPool){
        jdbcDAO = () -> {
            try {
                return connectionPool.getConnection();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        };
    }

    public Optional<Order> addOrder(Order order){
        return Optional.ofNullable(
                jdbcDAO.mapPreparedStatement(preparedStatement -> {
                    try {
                        preparedStatement.executeUpdate();
                        return order;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }, ADD_ORDER, order.getDate(), order.getActive(), order.getTourId(), order.getUserId())
        );
    }

    public Optional<Order> getOrderById(int id){
        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Order()
                            .setId(id)
                            .setDate(resultSet.getString(DATE))
                            .setActive(resultSet.getString(ACTIVE))
                            .setTourId(resultSet.getInt(TOUR_ID))
                            .setUserId(resultSet.getInt(USER_ID));
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, GET_ORDER_BY_ID, id));
    }

    public Optional<Order> getOrderByTourId(int tourId){
        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Order()
                            .setId(resultSet.getInt(ID))
                            .setDate(resultSet.getString(DATE))
                            .setActive(resultSet.getString(ACTIVE))
                            .setTourId(tourId)
                            .setUserId(resultSet.getInt(USER_ID));
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, GET_ORDER_BY_TOUR_ID, tourId));
    }

    public Optional<Order> getOrderByUserId(int userId){
        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Order()
                            .setId(resultSet.getInt(ID))
                            .setDate(resultSet.getString(DATE))
                            .setActive(resultSet.getString(ACTIVE))
                            .setTourId(resultSet.getInt(TOUR_ID))
                            .setUserId(userId);
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, GET_ORDER_BY_USER_ID, userId));
    }

    public int deleteOrderById(int id){
        return jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try {
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        }, DELETE_ORDER_BY_ID, id);
    }

    public Optional<Order> updateOrder(Order order){
        Optional<Order> optionalOrder = getOrderById(order.getId());
        if (!optionalOrder.isPresent()) {
            return Optional.empty();
        }
        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Order()
                            .setId(resultSet.getInt(ID))
                            .setDate(resultSet.getString(DATE))
                            .setActive(resultSet.getString(ACTIVE))
                            .setTourId(resultSet.getInt(TOUR_ID))
                            .setUserId(resultSet.getInt(USER_ID));
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, UPDATE_ORDER, order.getDate(), order.getActive(), order.getTourId(), order.getUserId(), order.getId()));

    }

}
