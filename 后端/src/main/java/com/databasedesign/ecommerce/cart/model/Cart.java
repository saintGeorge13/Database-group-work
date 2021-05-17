package com.databasedesign.ecommerce.cart.model;

import com.databasedesign.ecommerce.commodity.model.Commodity;
import com.databasedesign.ecommerce.user.model.User;

public class Cart {

    private Long id;

    private User customer;

    private Commodity commodity;

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", customer=" + customer +
                ", commodity=" + commodity +
                '}';
    }

    public Cart() {
    }

    public Cart(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }
}
