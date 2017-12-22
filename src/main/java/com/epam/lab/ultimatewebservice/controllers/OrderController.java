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

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/orders")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final OrderService orderService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getOrderById(@PathVariable(value = "id") int id, HttpServletRequest request) {
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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/createOrder");
        modelAndView.addObject("order", new Order());
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createOrder(@ModelAttribute("order") Order order, Model model,
                                    HttpServletRequest request){
        if(order.getUserId() == 0){
            model.addAttribute("errorMessage", "Error when we try to create order");
            return "order/error";
        }
        if(order.getTourId() == 0){
            model.addAttribute("errorMessage", "Error when we try to create order");
            return "order/error";
        }
        if(order.getDate() == null){
            model.addAttribute("errorMessage", "Error when we try to create order");
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

}
