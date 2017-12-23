package com.epam.lab.ultimatewebservice.db.dao;

import com.epam.lab.ultimatewebservice.db.connpool.ConnectionPool;
import com.epam.lab.ultimatewebservice.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class OrderDAO {

    private static final String ADD_ORDER =
            "INSERT INTO orders(date, active, tourId, userId) VALUES(?,?,?,?)";
    private static final String GET_ORDER_BY_ID =
            "SELECT date, active, tourId, userId FROM orders WHERE id=?";
    private static final String GET_ORDERS_BY_TOUR_ID =
            "SELECT id, date, active, tourId, userId FROM orders WHERE tourId=?";
    private static final String GET_ORDERS_BY_USER_ID =
            "SELECT id, date, active, tourId, userId FROM orders WHERE userId=?";
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
                jdbcDAO.mapPreparedStatementFlagged(preparedStatement -> {
                    try {
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                    try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                        return !rs.next() ? null : new Order()
                                .setId(rs.getInt(1))
                                .setDate(order.getDate())
                                .setActive(order.isActive())
                                .setTourId(order.getTourId())
                                .setUserId(order.getUserId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }, ADD_ORDER, order.getDate(), order.isActive(), order.getTourId(), order.getUserId())
        );
    }

    public Optional<Order> getOrderById(int id){
        return Optional.ofNullable(jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Order()
                            .setId(id)
                            .setDate(resultSet.getString(DATE))
                            .setActive(resultSet.getBoolean(ACTIVE))
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

    public List<Order> getOrdersByTourId(int tourId){
        List<Order> orderList = new ArrayList<>();
        jdbcDAO.withPreparedStatement(preparedStatement -> {
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    orderList.add(new Order()
                            .setId(rs.getInt(ID))
                            .setDate(rs.getString(DATE))
                            .setActive(rs.getBoolean(ACTIVE))
                            .setTourId(rs.getInt(TOUR_ID))
                            .setUserId(rs.getInt(USER_ID))
                    );
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }, GET_ORDERS_BY_TOUR_ID, tourId);
        return orderList;
    }

    public List<Order> getOrdersByUserId(int userId){
        List<Order> orderList = new ArrayList<>();
        jdbcDAO.withPreparedStatement(preparedStatement -> {
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    orderList.add(new Order()
                            .setId(rs.getInt(ID))
                            .setDate(rs.getString(DATE))
                            .setActive(rs.getBoolean(ACTIVE))
                            .setTourId(rs.getInt(TOUR_ID))
                            .setUserId(rs.getInt(USER_ID))
                    );
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }, GET_ORDERS_BY_USER_ID, userId);
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

    public Optional<Order> updateOrder(Order newValue){
        Optional<Order> orderToUpdate = getOrderById(newValue.getId());
        if (!orderToUpdate.isPresent()) {
            return Optional.empty();
        }
        Order oldValue = orderToUpdate.get();
        Map<String, String> toUpdate = getFieldsToUpdate(newValue, oldValue);
        if(toUpdate.size() == 0){
            return Optional.ofNullable(oldValue);
        }
        StringBuilder SQLRequest = new StringBuilder("UPDATE orders SET ");
        for (Iterator<Map.Entry<String, String>> iterator = toUpdate.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String, String> param = iterator.next();
            SQLRequest.append(param.getKey()).append("=?");
            if (iterator.hasNext()) {
                SQLRequest.append(", ");
            } else {
                SQLRequest.append(" ");
            }
        }
        SQLRequest.append(" WHERE id=?");
        toUpdate.put(ID, String.valueOf(newValue.getId()));
        return jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try {
                preparedStatement.executeUpdate();
                return Optional.of(oldValue);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, SQLRequest.toString(), toUpdate.values().toArray());
    }

    private Map<String, String> getFieldsToUpdate(Order newValue, Order oldValue){
        Map<String, String> toUpdate = new LinkedHashMap<>();
        if (newValue.getDate() != null &&
                !oldValue.getDate().equals(newValue.getDate().trim())
                && !newValue.getDate().isEmpty()) {
            toUpdate.put(DATE, newValue.getDate());
            oldValue.setDate(newValue.getDate());
        }
        if (oldValue.isActive() != newValue.isActive()) {
            toUpdate.put(ACTIVE, String.valueOf(newValue.isActive()));
            oldValue.setActive(newValue.isActive());
        }
        if (oldValue.getTourId() != newValue.getTourId()) {
            toUpdate.put(TOUR_ID, String.valueOf(newValue.getTourId()));
            oldValue.setTourId(newValue.getTourId());
        }
        if (oldValue.getUserId() != newValue.getUserId()) {
            toUpdate.put(USER_ID, String.valueOf(newValue.getUserId()));
            oldValue.setUserId(newValue.getUserId());
        }
        return toUpdate;
    }

}
