package com.databasedesign.ecommerce.commodity.model;

import com.databasedesign.ecommerce.order.model.Comment;
import com.databasedesign.ecommerce.order.model.Order;
import com.databasedesign.ecommerce.user.model.User;

import java.util.List;

public class Commodity {

    private Long id;

    private String name;

    private String type;

    private double price;

    private String picture;

    private String description;

    private double expressFee;

    private User seller;

    private List<Comment> commentList;

    @Override
    public String toString() {
        return "Commodity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", picture='" + picture + '\'' +
                ", description='" + description + '\'' +
                ", expressFee=" + expressFee +
                ", seller=" + seller +
                '}';
    }

    public Commodity() {
    }

    public Commodity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(double expressFee) {
        this.expressFee = expressFee;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}
