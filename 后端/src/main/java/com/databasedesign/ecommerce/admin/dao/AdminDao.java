package com.databasedesign.ecommerce.admin.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao {
    public int selectCount(@Param("account") String account, @Param("password") String password);
}
