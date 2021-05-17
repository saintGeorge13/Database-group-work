package com.databasedesign.ecommerce.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.databasedesign.ecommerce.admin.dao.AdminDao;
import com.databasedesign.ecommerce.commodity.dao.CommodityDao;
import com.databasedesign.ecommerce.commodity.model.Commodity;
import com.databasedesign.ecommerce.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    public boolean login(String account, String password){
        if (adminDao.selectCount(account, password) != 1){
            return false;
        }
        return true;
    }
}
