package com.epam.lab.ultimatewebservice.db.dao;

import com.epam.lab.ultimatewebservice.db.connpool.ConnectionPool;
import com.epam.lab.ultimatewebservice.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class OrderDAO {

    private static final String ADD_ORDER =
            "INSERT INTO orders(date, active, tour_id, user_id) VALUES(?,?,?,?)";
    private static final String GET_ORDER_BY_ID =
            "SELECT date, active, tour_id, user_id FROM orders WHERE id=?";
    private static final String GET_ORDERS_BY_TOUR_ID =
            "SELECT id, date, active, tour_id, user_id FROM orders WHERE tour_id=?";
    private static final String GET_ORDERS_BY_USER_ID =
            "SELECT id, date, active, tour_id, user_id FROM orders WHERE user_id=?";
    private static final String DELETE_ORDER_BY_ID =
            "DELETE FROM orders WHERE id=?";
    private static final String GET_ALL_ORDERS =
            "SELECT id, date, active, tour_id, user_id FROM orders";

    private static final String ID = "id";
    private static final String DATE = "date";
    private static final String ACTIVE = "active";
    private static final String TOUR_ID = "tour_id";
    private static final String USER_ID = "user_id";

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

    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        jdbcDAO.withResultSet(rs -> {
            try {
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
                throw new RuntimeException("Got an exception");

            }
        }, GET_ALL_ORDERS);
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
        //Map<String, String> toUpdate = getFieldsToUpdate(newValue, oldValue);
        List<String> toUpdate = whatFieldsToUpdate(newValue, oldValue);
        if(toUpdate.size() == 0){
            return Optional.ofNullable(oldValue);
        }
        StringBuilder SQLRequest = new StringBuilder("UPDATE orders SET ");
//        for (Iterator<Map.Entry<String, String>> iterator = toUpdate.entrySet().iterator(); iterator.hasNext();) {
//            Map.Entry<String, String> param = iterator.next();
//            SQLRequest.append(param.getKey()).append("=?");
//            if (iterator.hasNext()) {
//                SQLRequest.append(", ");
//            } else {
//                SQLRequest.append(" ");
//            }
//        }
//        SQLRequest.append(" WHERE id=?");
//        toUpdate.put(ID, String.valueOf(newValue.getId()));
        for(Iterator<String> iterator = toUpdate.iterator(); iterator.hasNext();) {
            String v = iterator.next();
            SQLRequest.append(v).append("=?");
            if (iterator.hasNext()) {
                SQLRequest.append(", ");
            } else {
                SQLRequest.append(" ");
            }
        }
        SQLRequest.append(" WHERE id=?");
        return jdbcDAO.mapPreparedStatement(preparedStatement -> {
            try {
                for (Iterator<String> iterator = toUpdate.iterator(); iterator.hasNext(); ) {
                    String v = iterator.next();
                    int i = 1;
                    switch (v) {
                        case DATE:
                            preparedStatement.setString(i, oldValue.getDate());
                            break;
                        case ACTIVE:
                            preparedStatement.setBoolean(i, oldValue.isActive());
                            break;
                        case TOUR_ID:
                            preparedStatement.setInt(i, oldValue.getTourId());
                            break;
                        case USER_ID:
                            preparedStatement.setInt(i, oldValue.getUserId());
                            break;
                    }
                    i++;
                    if(!iterator.hasNext())
                        preparedStatement.setInt(i, oldValue.getId());
                }
                preparedStatement.executeUpdate();
                return Optional.of(oldValue);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }, SQLRequest.toString());
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

    private List<String> whatFieldsToUpdate(Order newValue, Order oldValue) {
        List<String> toUpdate = new ArrayList<>();
        if (newValue.getDate() != null &&
                !oldValue.getDate().equals(newValue.getDate().trim())
                && !newValue.getDate().isEmpty()) {
            toUpdate.add(DATE);
            oldValue.setDate(newValue.getDate());
        }
        if (oldValue.isActive() != newValue.isActive()) {
            toUpdate.add(ACTIVE);
            oldValue.setActive(newValue.isActive());
        }
        if (newValue.getUserId() != 0 && oldValue.getTourId() != newValue.getTourId()) {
            toUpdate.add(TOUR_ID);
            oldValue.setTourId(newValue.getTourId());
        }
        if (newValue.getUserId() != 0 && oldValue.getUserId() != newValue.getUserId()) {
            toUpdate.add(USER_ID);
            oldValue.setUserId(newValue.getUserId());
        }
        return toUpdate;
    }

}
