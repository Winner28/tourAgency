package com.epam.lab.ultimatewebservice.controllers;

import com.epam.lab.ultimatewebservice.entity.Order;
import com.epam.lab.ultimatewebservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/orders")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final OrderService orderService;
    private static final String LOGGED_COOKIE = "userLoggedIn";

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getOrderById(@PathVariable(value = "id") int id, HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        Order order = orderService.getOrderById(id);
        ModelAndView model = new ModelAndView();
        if (order == null) {
            model.setViewName("order/error");
            model.addObject("errorMessage", "Order don't exist");
            return model;
        }
        model.setViewName("order/showOrder");
        model.addObject("order", order);
        return model;
    }


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createOrdersPage(HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/createOrder");
        modelAndView.addObject("order", new Order());
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createOrder(@ModelAttribute("order") Order order, Model model,
                                    HttpServletRequest request){
        if(order.getUserId() == 0){
            model.addAttribute("errorMessage", "UserId is not defined!");
            return "order/error";
        }
        if(order.getTourId() == 0){
            model.addAttribute("errorMessage", "TourId is not defined!");
            return "order/error";
        }
        if(order.getDate() == null){
            model.addAttribute("errorMessage", "Date is not defined!");
            return "order/error";
        }

        Order createdOrder = orderService.addOrder(order);

        if(createdOrder == null){
            model.addAttribute("errorMessage", "Error when we try to create order");
            return "order/error";
        }

        model.addAttribute("order", createdOrder);
        model.addAttribute("message", "Order successfully created!");
        return "order/showOrder";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ModelAndView deleteOrderById(@PathVariable String id){
        Order orderToDelete = orderService.getOrderById(Integer.parseInt(id));
        if (orderToDelete == null) {
            return new ModelAndView("order/error", "errorMessage",
                    "Order with such id don't exist");
        }
        if (!orderService.deleteOrder(Integer.parseInt(id))) {
            return new ModelAndView("order/error", "errorMessage",
                    "Something goes wrong when we trying to delete order");
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/showOrder");
        modelAndView.addObject("order", orderToDelete);
        modelAndView.addObject("message", "Order with ID = " +
                orderToDelete.getId() + " successfully deleted!");
        return modelAndView;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView updateOrderPage(@PathVariable int id, HttpServletRequest request){
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        ModelAndView modelAndView = new ModelAndView();
        Order order = orderService.getOrderById(id);
        if (order == null) {
            modelAndView.setViewName("order/error");
            modelAndView.addObject("errorMessage", "There is no order with such id");
            return modelAndView;
        }
        modelAndView.setViewName("order/updateOrder");
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateOrder(@ModelAttribute("order") Order order, Model model,
                              HttpServletRequest request){
        order.setId(Integer.parseInt(request.getParameter("id")));
        if(order.getUserId() == 0){
            model.addAttribute("errorMessage", "UserId is not defined!");
            return "order/error";
        }
        if(order.getTourId() == 0){
            model.addAttribute("errorMessage", "TourId is not defined!");
            return "order/error";
        }
        if(order.getDate() == null){
            model.addAttribute("errorMessage", "Date is not defined!");
            return "order/error";
        }
        Order updatedOrder = orderService.updateOrder(order);
        if(updatedOrder == null){
            model.addAttribute("errorMessage", "Error when we try to update order");
            return "order/error";
        }
        model.addAttribute("message", "Order successfully updated!");
        model.addAttribute("order", updatedOrder);
        return "order/showOrder";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView getAllOrders(HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        ModelAndView modelAndView = new ModelAndView();
        Cookie[]cookies = request.getCookies();
        int id = 0;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LOGGED_COOKIE)) {
                id = SessionManager.getUserIdByCookie(cookie);
            }
        }
        if (id == 0) {
            modelAndView.setViewName("order/error");
            modelAndView.addObject("errorMessage", "No such User logged in");
            return modelAndView;
        }
        List<Order> orderList = orderService.getOrdersByUserId(id);
        if (orderList == null) {
            modelAndView.setViewName("order/error");
            modelAndView.addObject("errorMessage", "THERE IS NULL");
            return modelAndView;
        }
        modelAndView.setViewName("order/showAllOrders");
        modelAndView.addObject("orderList", orderList);
        return modelAndView;
    }

    private ModelAndView accessDeniedView() {
        return new ModelAndView("order/error","errorMessage",
                "Bad access. Your request denied");
    }

    private boolean checkAccess(HttpServletRequest request) {
        Cookie[]cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LOGGED_COOKIE)) {
                int id = SessionManager.getUserIdByCookie(cookie);
                if (id == 0)
                    return false;
                return true;
            }
        }
        return false;
    }

}
