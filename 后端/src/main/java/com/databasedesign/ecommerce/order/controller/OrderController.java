package com.databasedesign.ecommerce.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.databasedesign.ecommerce.commodity.model.Commodity;
import com.databasedesign.ecommerce.order.model.Comment;
import com.databasedesign.ecommerce.order.model.Order;
import com.databasedesign.ecommerce.order.service.OrderService;
import com.databasedesign.ecommerce.user.model.User;
import com.databasedesign.ecommerce.utils.DateUtils;
import com.databasedesign.ecommerce.utils.Result;
import com.databasedesign.ecommerce.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public Result addOrder(HttpServletRequest request, @RequestBody JSONObject inputData){
        Collection<String> paramNeeded = new HashSet<String>(){{
            add("commodityID");
        }};
        if (!inputData.keySet().containsAll(paramNeeded)){
            return new Result("参数错误");
        }
        String customerID = (String) request.getSession().getAttribute("studentID");
        Long commodityID = inputData.getLong("commodityID");
        String date = DateUtils.getDateTime();
        Order order = new Order();
        order.setCustomer(new User(customerID));
        order.setCommodity(new Commodity(commodityID));
        order.setDate(date);
        return orderService.addOrder(order);
    }

    @PutMapping("/orders")
    public Result editReception(HttpServletRequest request, @RequestBody JSONObject inputData){
        Collection<String> paramNeeded = new HashSet<String>(){{
            add("orderID");
            add("reception");
        }};
        if (!inputData.keySet().containsAll(paramNeeded)){
            return new Result("参数错误");
        }
        String customerID = (String) request.getSession().getAttribute("studentID");
        Long orderID = inputData.getLong("orderID");
        String reception = inputData.getString("reception");
        Order order = new Order(orderID);
        order.setReception(reception);
        order.setCustomer(new User(customerID));
        return orderService.editReception(order);
    }

    @GetMapping("/orders")
    public Result getOrders(HttpServletRequest request){
        String baseURL = WebUtils.getBaseURL(request);
        return orderService.getOrders(baseURL, (String) request.getSession().getAttribute("studentID"));
    }

    @PostMapping("/comments")
    public Result addComment(HttpServletRequest request, @RequestBody JSONObject inputData){
        Collection<String> paramNeeded = new HashSet<String>(){{
            add("orderID");
            add("content");
            add("star");
        }};
        if (!inputData.keySet().containsAll(paramNeeded)){
            return new Result("参数错误");
        }
        String customerID = (String) request.getSession().getAttribute("studentID");
        Comment comment = new Comment();
        Order order = new Order(inputData.getLong("orderID"));
        order.setCustomer(new User(customerID));
        comment.setOrder(order);
        comment.setContent(inputData.getString("content"));
        comment.setStar(inputData.getIntValue("star"));
        return orderService.addComment(comment);
    }
}
