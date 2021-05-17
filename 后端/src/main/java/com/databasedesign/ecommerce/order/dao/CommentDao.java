package com.databasedesign.ecommerce.order.dao;

import com.databasedesign.ecommerce.order.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao {

    /**
     * 添加评论
     * @param comment
     * @return
     */
    public int insertComment(Comment comment);

    /**
     * 根据卖家ID获取评论
     * @param sellerID
     * @return
     */
    public List<Comment> selectBySellerID(String sellerID);
}
