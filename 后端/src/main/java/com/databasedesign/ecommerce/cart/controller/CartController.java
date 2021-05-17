package com.databasedesign.ecommerce.cart.controller;

import com.alibaba.fastjson.JSONObject;
import com.databasedesign.ecommerce.cart.model.Cart;
import com.databasedesign.ecommerce.cart.service.CartService;
import com.databasedesign.ecommerce.commodity.model.Commodity;
import com.databasedesign.ecommerce.user.model.User;
import com.databasedesign.ecommerce.utils.Result;
import com.databasedesign.ecommerce.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/carts")
    public Result addCart(HttpServletRequest request, @RequestBody JSONObject inputData){
        Collection<String> paramNeeded = new HashSet<String>(){{
            add("commodityID");
        }};
        if (!inputData.keySet().containsAll(paramNeeded)){
            return new Result("参数错误");
        }
        String customerID = (String) request.getSession().getAttribute("studentID");
        Long commodityID = inputData.getLong("commodityID");
        Cart cart = new Cart();
        cart.setCustomer(new User(customerID));
        cart.setCommodity(new Commodity(commodityID));
        return cartService.addCart(cart);
    }

    @GetMapping("/carts")
    public Result getByCustomerID(HttpServletRequest request){
        String baseURL = WebUtils.getBaseURL(request);
        String customerID = (String) request.getSession().getAttribute("studentID");
        return cartService.getByCustomerID(baseURL, customerID);
    }

    @DeleteMapping("/carts/{id}")
    public Result removeCart(@PathVariable("id") Long cartID){
        return cartService.removeCart(cartID);
    }
}
