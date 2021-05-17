package com.databasedesign.ecommerce.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.databasedesign.ecommerce.admin.service.AdminService;
import com.databasedesign.ecommerce.commodity.service.CommodityService;
import com.databasedesign.ecommerce.order.service.OrderService;
import com.databasedesign.ecommerce.utils.Result;
import com.databasedesign.ecommerce.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashSet;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/admin_login")
    public Result login(HttpServletRequest request, @RequestBody JSONObject inputData){
        Collection<String> paramNeeded = new HashSet<String>(){{
            add("account");
            add("password");
        }};
        if (!inputData.keySet().containsAll(paramNeeded)){
            return new Result("参数错误");
        }
        String account = inputData.getString("account");
        String password = inputData.getString("password");
        if (!adminService.login(account, password)){
            return new Result("账号或密码错误");
        }
        HttpSession session = request.getSession();
        session.setAttribute("admin", true);
        return new Result();
    }

    @GetMapping("admin_commodities")
    public Result getAllCommodities(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null){
            return new Result("无权限");
        }
        String baseURL = WebUtils.getBaseURL(request);
        return commodityService.getAllCommodities(baseURL);
    }

    @DeleteMapping("admin_commodities/{id}")
    public Result removeCommodity(HttpServletRequest request,@PathVariable("id") Long commodityID){
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null){
            return new Result("无权限");
        }
        return commodityService.removeCommodity(commodityID);
    }

    @GetMapping("admin_orders")
    public Result getAllOrders(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null){
            return new Result("无权限");
        }
        String baseURL = WebUtils.getBaseURL(request);
        return orderService.getOrders(baseURL);
    }
}
