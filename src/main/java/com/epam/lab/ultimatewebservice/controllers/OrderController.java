package com.epam.lab.ultimatewebservice.controllers;

import com.epam.lab.ultimatewebservice.entity.Order;
import com.epam.lab.ultimatewebservice.entity.Tour;
import com.epam.lab.ultimatewebservice.service.OrderService;
import com.epam.lab.ultimatewebservice.service.TourService;
import com.epam.lab.ultimatewebservice.service.UserService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/orders")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final TourService tourService;
    private final OrderService orderService;
    private final UserService userService;
    private static final String LOGGED_COOKIE = "userLoggedIn";

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getOrderById(@PathVariable int id, HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        Order order = orderService.getOrderById(id);
        ModelAndView model = new ModelAndView();
        if (order == null) {
            model.setViewName("errors/error");
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
                              HttpServletRequest request) {
        if (!checkAccess(request)) {
            model.addAttribute("errorMessage", "Bad access. Your request denied!");
            return "errors/error";
        }

        int tourId = Integer.parseInt(request.getParameter("tourId"));
        order.setTourId(tourId);
        order.setUserId(getUserId(request));
        order.setDate(new Date().toString());
        order.setActive(true);
        System.out.println(order);
        if (!checkOrder(order)) {
            model.addAttribute("errorMessage", "Order has wrong parameters!");
            return "errors/error";
        }

        Order createdOrder = orderService.addOrder(order);

        if (createdOrder == null) {
            model.addAttribute("errorMessage", "Error when we try to create order");
            return "errors/error";
        }

        model.addAttribute("order", createdOrder);
        model.addAttribute("message", "Order successfully created!");
        model.addAttribute("tourService", tourService);
        return "order/showOrder";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ModelAndView deleteOrderById(@PathVariable int id) {
        if (!checkOrderExistence(id)) {
            return new ModelAndView("errors/error", "errorMessage",
                    "Order with such id don't exist");
        }
        if (!orderService.deleteOrder(id)) {
            return new ModelAndView("errors/error", "errorMessage",
                    "Something goes wrong when we trying to delete order");
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/showOrder");
        modelAndView.addObject("order", orderService.getOrderById(id));
        modelAndView.addObject("message", "Order with ID = " +
                id + " successfully deleted!");
        return modelAndView;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView updateOrderPage(@PathVariable int id, HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        ModelAndView modelAndView = new ModelAndView();
        Order order = orderService.getOrderById(id);
        if (order == null) {
            modelAndView.setViewName("errors/error");
            modelAndView.addObject("errorMessage", "There is no order with such id");
            return modelAndView;
        }
        modelAndView.setViewName("order/updateOrder");
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateOrder(@ModelAttribute("order") Order order, Model model,
                              HttpServletRequest request) {
        order.setId(Integer.parseInt(request.getParameter("id")));
        order.setDate(request.getParameter("date"));
        order.setActive(Boolean.parseBoolean(request.getParameter("active")));
        order.setTourId(Integer.parseInt(request.getParameter("tourId")));
        order.setUserId(Integer.parseInt(request.getParameter("userId")));
        if (!checkOrder(order)) {
            model.addAttribute("errorMessage", "Order has wrong parameters!");
            return "errors/error";
        }
        if (!checkOrderExistence(order.getId())) {
            model.addAttribute("errorMessage", "Order with such id don't exist!");
            return "errors/error";
        }
        Order updatedOrder = orderService.updateOrder(order);
        if (updatedOrder == null) {
            model.addAttribute("errorMessage", "Error when we try to update order");
            return "errors/error";
        }
        model.addAttribute("message", "Order successfully denied!");
        model.addAttribute("order", updatedOrder);
        return "order/showOrder";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView getAllUserOrders(HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        ModelAndView modelAndView = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        int id = getIdByCookie(cookies);

        if (id == 0) {
            modelAndView.setViewName("errors/error");
            modelAndView.addObject("errorMessage", "No such User logged in");
            return modelAndView;
        }
        List<Order> orders = orderService.getOrdersByUserId(id);
        if (orders == null) {
            modelAndView.setViewName("errors/error");
            modelAndView.addObject("errorMessage", "THERE IS NULL");
            return modelAndView;
        }
        List<Order> orderList = new ArrayList<>();
        for (Order o: orders) {
            if(o.isActive())
                orderList.add(o);
        }
        modelAndView.setViewName("order/showAllOrders");
        modelAndView.addObject("orderList", orderList);
        modelAndView.addObject("tourService", tourService);
        modelAndView.addObject("discount", orderService.getDiscount(id));
        return modelAndView;
    }

    @RequestMapping(value = "/agentOrders", method = RequestMethod.GET)
    public ModelAndView getAllAgentOrders(HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        ModelAndView modelAndView = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        int id = getIdByCookie(cookies);

        if (id == 0) {
            modelAndView.setViewName("errors/error");
            modelAndView.addObject("errorMessage", "No such User logged in");
            return modelAndView;
        }

        List<Tour> agentTours = tourService.getToursIdByAgentId(id);
        List<Order> tmp = orderService.getAllOrders();
        List<Order> agentOrders = new ArrayList<>();
        for (Tour tour : agentTours){
            //agentOrders.addAll(orderService.getOrdersByTourId(tour.getId()));
            for(Order o: tmp) {
                if(o.getTourId() == tour.getId())
                    agentOrders.add(o);
            }
        }

//        if (agentOrders.size() == 0) {
//            modelAndView.setViewName("errors/error");
//            modelAndView.addObject("errorMessage", "There are no orders for you!");
//            return modelAndView;
//        }
        modelAndView.setViewName("order/showOrdersForAgent");
        modelAndView.addObject("orderList", agentOrders);
        modelAndView.addObject("tourService", tourService);
        modelAndView.addObject("userService", userService);
        return modelAndView;
    }

    @RequestMapping(value = "/allOrders", method = RequestMethod.GET)
    public ModelAndView getAllOrders(HttpServletRequest request) {
        if (!checkAccess(request)) {
            return accessDeniedView();
        }
        ModelAndView modelAndView = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        int id = getIdByCookie(cookies);

        if (id == 0) {
            modelAndView.setViewName("errors/error");
            modelAndView.addObject("errorMessage", "No such User logged in");
            return modelAndView;
        }

        List<Order> orderList = orderService.getAllOrders();
        modelAndView.setViewName("order/showOrdersForAgent");
        modelAndView.addObject("orderList", orderList);
        return modelAndView;
    }

    private int getIdByCookie(Cookie[] cookies){
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LOGGED_COOKIE)) {
                return SessionManager.getUserIdByCookie(cookie);
            }
        }
        return 0;
    }



    private ModelAndView accessDeniedView() {
        return new ModelAndView("errors/error", "errorMessage",
                "Bad access. Your request denied");
    }

    private boolean checkAccess(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LOGGED_COOKIE)) {
                int id = SessionManager.getUserIdByCookie(cookie);
                return id != 0;
            }
        }
        return false;
    }

    private int getUserId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LOGGED_COOKIE)) {
                int id = SessionManager.getUserIdByCookie(cookie);
                return id;
            }
        }
        return 0;
    }

    private boolean checkOrder(Order order) {
        if(order == null)
            return false;
        return !(order.getDate().isEmpty()) &&
                !(order.getTourId() == 0) && !(order.getUserId() == 0);
    }

    private boolean checkOrderExistence(int id){
        Order existenceOrder = orderService.getOrderById(id);
        if (existenceOrder == null) {
            return false;
        }
        return true;
    }

}
