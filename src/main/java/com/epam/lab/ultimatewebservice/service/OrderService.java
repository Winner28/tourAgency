package com.epam.lab.ultimatewebservice.service;

import com.epam.lab.ultimatewebservice.db.dao.OrderDAO;
import com.epam.lab.ultimatewebservice.entity.Order;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {

    private final OrderDAO orderDAO;

    public Order addOrder(Order order) {
        return orderDAO.addOrder(order).orElse(null);
    }

    public Order updateOrder(Order order) {
        return orderDAO.updateOrder(order).orElse(null);
    }

    public boolean deleteOrder(int id) {
        return orderDAO.deleteOrderById(id) > 0;
    }

    public List<Order> getOrdersByUserId(int id) {
        return orderDAO.getOrdersByUserId(id);
    }

    public Order getOrderById(int id) {
        return orderDAO.getOrderById(id).orElse(null);
    }

}
