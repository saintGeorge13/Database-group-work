package com.databasedesign.ecommerce.cart.dao;

import com.databasedesign.ecommerce.cart.model.Cart;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao {

    public int insertOne(Cart cart);

    public List<Cart> selectByCustomerID(String customerID);

    public int deleteOne(Long cartID);
}
