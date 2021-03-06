package com.epam.lab.ultimatewebservice.service;

import com.epam.lab.ultimatewebservice.db.dao.OrderDAO;
import com.epam.lab.ultimatewebservice.entity.Order;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public List<Order> getOrdersByTourId(int tourId) {
        return orderDAO.getOrdersByTourId(tourId);
    }

    public List<Order> getAllOrders () {
        return orderDAO.getAllOrders();
    }

    public Order getOrderById(int id) {
        return orderDAO.getOrderById(id).orElse(null);
    }

    public int getDiscount(int userId) {
        List<Order> orderList = getOrdersByUserId(userId);
        if (orderList.isEmpty()) {
            return 0;
        }
        int activeCount = 0;
        for (Order o: orderList) {
            if(o.isActive())
                activeCount++;
        }
        if (activeCount < 5) {
            return 0;
        }
        if (activeCount < 10) {
            return 5;
        }
        if (activeCount < 20) {
            return 10;
        }
        if (activeCount < 30) {
            return 15;
        } else
            return 20;
    }

}
