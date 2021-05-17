package com.databasedesign.ecommerce.commodity.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityTypeDao {

    public List<String> selectAllTypes();

    public int selectCountByType(String type);
}
