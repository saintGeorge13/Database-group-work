package com.databasedesign.ecommerce.order.service;

import com.alibaba.fastjson.JSONObject;
import com.databasedesign.ecommerce.commodity.dao.CommodityDao;
import com.databasedesign.ecommerce.commodity.model.Commodity;
import com.databasedesign.ecommerce.order.dao.CommentDao;
import com.databasedesign.ecommerce.order.dao.OrderDao;
import com.databasedesign.ecommerce.order.model.Comment;
import com.databasedesign.ecommerce.order.model.Order;
import com.databasedesign.ecommerce.user.model.User;
import com.databasedesign.ecommerce.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private CommodityDao commodityDao;

    @Value("${file.commodityPicture.staticAccessPath}")
    private String commodityAccessPath;

    @Transactional
    public Result addOrder(Order order){
        if (commodityDao.updateBought(order.getCommodity().getId()) != 1){
            return new Result("创建订单失败");
        }
        if (orderDao.insertOrder(order) != 1){
            return new Result("创建订单失败");
        }
        return new Result();
    }

    public Result editReception(Order order){
        String reception = order.getReception();
        if (!reception.equals("是") && !reception.equals("否")){
            return new Result("参数无效");
        }
        if (orderDao.updateReception(order) != 1){
            return new Result("更新失败");
        }
        return new Result();
    }

    public Result getOrders(String baseURL, String customerID){
        List<Order> orders = orderDao.selectByCustomerID(customerID);
        return polishOrders(baseURL, orders);
    }
    @Transactional
    public Result addComment(Comment comment){
        int star = comment.getStar();
        if (star < 1 || star > 5){
            return new Result("评分错误");
        }
        if (orderDao.checkReception(comment.getOrder()) != 1){
            return new Result("添加评论失败");
        }
        if (commentDao.insertComment(comment) != 1){
            return new Result("添加评论失败");
        }
        return new Result();
    }

    public Result getOrders(String baseURL){
        List<Order> orders = orderDao.selectAll();
        return polishOrders(baseURL, orders);
    }

    private Result polishOrders(String baseURL, List<Order> orders) {
        List<JSONObject> data = new ArrayList<>();
        for (Order order : orders){
            JSONObject jsonObject = new JSONObject();
            Commodity commodity = order.getCommodity();
            commodity.setPicture(baseURL + commodityAccessPath + commodity.getPicture());
            User seller = commodity.getSeller();
            Comment comment = order.getComment();
            jsonObject.put("orderID", order.getId());
            jsonObject.put("sellerName", seller.getName());
            jsonObject.put("commodityName", commodity.getName());
            jsonObject.put("commodityPrice", commodity.getPrice());
            jsonObject.put("commodityPicture", commodity.getPicture());
            jsonObject.put("orderDate", order.getDate());
            jsonObject.put("orderReception", order.getReception());
            jsonObject.put("comment", comment == null ? null : comment.getContent());
            data.add(jsonObject);
        }
        return new Result(data);
    }

}
