package com.databasedesign.ecommerce.commodity.dao;

import com.databasedesign.ecommerce.commodity.model.Commodity;
import com.databasedesign.ecommerce.order.model.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityDao {

    /**
     * 插入商品信息
     * @param commodity
     * @return
     */
    public int insertOne(Commodity commodity);

    /**
     * 更新图片路径
     * @param id
     * @param picture
     * @return
     */
    public int updatePicture(@Param("id") Long id, @Param("picture") String picture);

    public int updateBought(Long id);

    /**
     * 随机获取10个商品
     * @return
     */
    public List<Commodity> selectByRandom();

    /**
     * 通过关键字获取商品列表
     * @param keyword
     * @return
     */
    public List<Commodity> selectByKeyword(String keyword);

    public List<Commodity> selectBySellerID(String sellerID);

    /**
     * 根据商品ID获取商品
     * @param id
     * @return
     */
    public Commodity selectByID(Long id);

//    public List<Comment> selectCommentsByID(Long commodityID);

    public int deleteOne(@Param("commodityID") Long commodityID, @Param("sellerID") String sellerID);

    public List<Commodity> selectAll();

    public int adminDeleteOne(Long commodityID);
}
