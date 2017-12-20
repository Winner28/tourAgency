package com.epam.lab.ultimatewebservice.db.dao;

import com.epam.lab.ultimatewebservice.db.connpool.ConnectionPool;
import com.epam.lab.ultimatewebservice.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDAO {

    private static final String ADD_ORDER =
            "INSERT INTO orders(date, active, tourId, userId) VALUES(?,?,?,?)";
    private static final String GET_ORDER_BY_ID =
            "SELECT (date, active, tourId, userId) FROM orders WHERE id=?";
    private static final String GET_ORDERS_BY_TOUR_ID =
            "SELECT (id, date, active, tourId, userId) FROM orders WHERE tourId=?";
    private static final String GET_ORDERS_BY_USER_ID =
            "SELECT (id, date, active, tourId, userId) FROM orders WHERE userId=?";
    private static final String DELETE_ORDER_BY_ID =
            "DELETE FROM orders WHERE id=?";
    private static final String UPDATE_ORDER = "UPDATE orders SET date=?, active=?, tourId=?, userId=? WHERE id=?";

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

    public List<Order> getOrderByTourId(int tourId){
        List<Order> orderList = new ArrayList<>();
        jdbcDAO.withResultSet(rs -> {
            try {
                while (rs.next()) {
                    orderList.add(new Order()
                            .setId(rs.getInt(ID))
                            .setDate(rs.getString(DATE))
                            .setActive(rs.getString(ACTIVE))
                            .setTourId(rs.getInt(TOUR_ID))
                            .setUserId(rs.getInt(USER_ID))
                    );
                }
            }catch (SQLException e) {
                throw new RuntimeException("Got an exception");
            }
        }, GET_ORDERS_BY_TOUR_ID);
        return orderList;
    }

    public List<Order> getOrderByUserId(int userId){
        List<Order> orderList = new ArrayList<>();
        jdbcDAO.withResultSet(rs -> {
            try {
                while (rs.next()) {
                    orderList.add(new Order()
                            .setId(rs.getInt(ID))
                            .setDate(rs.getString(DATE))
                            .setActive(rs.getString(ACTIVE))
                            .setTourId(rs.getInt(TOUR_ID))
                            .setUserId(rs.getInt(USER_ID))
                    );
                }
            }catch (SQLException e) {
                throw new RuntimeException("Got an exception");
            }
        }, GET_ORDERS_BY_USER_ID);
        return orderList;
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
        Optional<Order> orderToUpdate = getOrderById(order.getId());
        if (!orderToUpdate.isPresent()) {
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
