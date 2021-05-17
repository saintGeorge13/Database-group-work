package com.databasedesign.ecommerce.cart.service;

import com.alibaba.fastjson.JSONObject;
import com.databasedesign.ecommerce.cart.dao.CartDao;
import com.databasedesign.ecommerce.cart.model.Cart;
import com.databasedesign.ecommerce.commodity.model.Commodity;
import com.databasedesign.ecommerce.user.model.User;
import com.databasedesign.ecommerce.utils.Result;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

    @Value("${file.commodityPicture.staticAccessPath}")
    private String pictureAccessPath;

    public Result addCart(Cart cart){
        if (cartDao.insertOne(cart) != 1){
            return new Result("加入购物车失败");
        }
        return new Result();
    }

    public Result getByCustomerID(String baseURL, String customerID){
        List<Cart> cartList = cartDao.selectByCustomerID(customerID);
        List<JSONObject> data = new ArrayList<>();
        for (Cart cart : cartList){
            JSONObject jsonObject = new JSONObject();
            Commodity commodity = cart.getCommodity();
            commodity.setPicture(baseURL + pictureAccessPath + commodity.getPicture());
            User seller = commodity.getSeller();
            jsonObject.put("cartID", cart.getId());
            jsonObject.put("commodityID", commodity.getId());
            jsonObject.put("commodityName", commodity.getName());
            jsonObject.put("commodityPrice", commodity.getPrice());
            jsonObject.put("commodityExpressFee", commodity.getExpressFee());
            jsonObject.put("commodityPicture", commodity.getPicture());
            jsonObject.put("sellerName", seller.getName());
            data.add(jsonObject);
        }
        return new Result(data);
    }

    public Result removeCart(Long cartID){
        if (cartDao.deleteOne(cartID) != 1){
            return new Result("删除失败");
        }
        return new Result();
    }
}
