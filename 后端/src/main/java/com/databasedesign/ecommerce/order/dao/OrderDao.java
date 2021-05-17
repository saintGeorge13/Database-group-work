package com.databasedesign.ecommerce.order.dao;

import com.databasedesign.ecommerce.order.model.Comment;
import com.databasedesign.ecommerce.order.model.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao {

    /**
     * 添加订单
     * @param order
     * @return
     */
    public int insertOrder(Order order);

    /**
     * 更新reception
     * @param order
     * @return
     */
    public int updateReception(Order order);

    /**
     * 根据customerID获取订单
     * @param customerID
     * @return
     */
    public List<Order> selectByCustomerID(String customerID);

    /**
     * 检测订单是否确认(0为否, 1为是)
     * @param order
     * @return
     */
    public int checkReception(Order order);

    public List<Order> selectAll();
}
