package com.databasedesign.ecommerce.commodity.service;

import com.alibaba.fastjson.JSONObject;
import com.databasedesign.ecommerce.commodity.dao.CommodityDao;
import com.databasedesign.ecommerce.commodity.dao.CommodityTypeDao;
import com.databasedesign.ecommerce.commodity.model.Commodity;
import com.databasedesign.ecommerce.order.dao.CommentDao;
import com.databasedesign.ecommerce.order.model.Comment;
import com.databasedesign.ecommerce.user.model.User;
import com.databasedesign.ecommerce.user.service.UserService;
import com.databasedesign.ecommerce.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommodityService {

    @Autowired
    private CommodityDao commodityDao;

    @Autowired
    private CommodityTypeDao commodityTypeDao;

    @Autowired
    private UserService userService;

    @Value("${file.commodityPicture.uploadPath}")
    private String pictureDir;

    @Value("${file.commodityPicture.staticAccessPath}")
    private String pictureAccessDir;

    /**
     * 获取所有商品类型
     * @return
     */
    @Transactional
    public Result getCommodityTypes(){
        return new Result(commodityTypeDao.selectAllTypes());
    }

    /**
     * 添加商品
     * @param commodity
     * @param picture
     * @return
     */
    @Transactional
    public Result addCommodity(Commodity commodity, MultipartFile picture){
        if (commodityTypeDao.selectCountByType(commodity.getType()) == 0){
            return new Result("商品类别错误");
        }
        if (commodityDao.insertOne(commodity) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result("添加商品失败");
        }
        Long id = commodity.getId();
        String fileName = id + ".jpg";
        if (commodityDao.updatePicture(id, fileName) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result("图片上传失败");
        }
        try {
            File file = new File(pictureDir + fileName);
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            out.write(picture.getBytes());
        } catch (FileNotFoundException e) {
            return new Result("图片上传失败");
        } catch (IOException e) {
            return new Result("图片上传失败");
        }
        return new Result();
    }

    /**
     * 随机获取10个商品
     * @param baseURL
     * @return
     */
    public Result getRandomCommodities(String baseURL){
        List<Commodity> commodities = commodityDao.selectByRandom();
        return getCommodityResult(baseURL, commodities);
    }

    /**
     * 通过关键词搜索商品
     * @param baseURL
     * @param keyword
     * @return
     */
    public Result getCommoditiesByKeyword(String baseURL, String keyword){
        List<Commodity> commodities = commodityDao.selectByKeyword(keyword);
        return getCommodityResult(baseURL, commodities);
    }

    public Result getCommoditiesBySellerID(String baseURL, String sellerID){
        List<Commodity> commodityList = commodityDao.selectBySellerID(sellerID);
        return getCommodityResult(baseURL, commodityList);
    }

    /**
     * 商品概述所需要的信息
     * @param baseURL
     * @param commodities
     * @return
     */
    private Result getCommodityResult(String baseURL, List<Commodity> commodities) {
        List<JSONObject> data = new ArrayList<>();
        for (Commodity commodity : commodities){
            commodity.setPicture(baseURL+ pictureAccessDir + commodity.getPicture());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("commodityID", commodity.getId());
            jsonObject.put("commodityName", commodity.getName());
            jsonObject.put("commodityType", commodity.getType());
            jsonObject.put("commodityPrice", commodity.getPrice());
            jsonObject.put("commodityPicture", commodity.getPicture());
            jsonObject.put("sellerName", commodity.getSeller().getName());
            data.add(jsonObject);
        }
        return new Result(data);
    }

    /**
     * 获取商品详细信息
     * @param baseURL
     * @param id
     * @return
     */
    public Result getCommodityByID(String baseURL, Long id){
        Commodity commodity = commodityDao.selectByID(id);
        if (commodity == null){
            return new Result("商品不存在");
        }
        commodity.setPicture(baseURL + pictureAccessDir + commodity.getPicture());
        User seller = commodity.getSeller();
        JSONObject data = new JSONObject();
        data.put("sellerName", seller.getName());
        data.put("commodityID", commodity.getId());
        data.put("commodityName", commodity.getName());
        data.put("commodityType", commodity.getType());
        data.put("commodityPrice", commodity.getPrice());
        data.put("commodityPicture", commodity.getPicture());
        data.put("commodityDescription", commodity.getDescription());
        data.put("commodityExpressFee", commodity.getExpressFee());
        data.put("comments", userService.getComments(seller.getStudentID()).getData());
        return new Result(data);
    }

//    /**
//     * 通过商品ID获取评论
//     * @param commodityID
//     * @return
//     */
//    public Result getCommentsByID(Long commodityID){
//        List<Comment> commentList = commodityDao.selectCommentsByID(commodityID);
//        List<JSONObject> data = new ArrayList<>();
//        for (Comment comment : commentList){
//            JSONObject jsonObject = new JSONObject();
//            User customer = comment.getOrder().getCustomer();
//            jsonObject.put("customerName", customer.getName());
//            jsonObject.put("commentContent", comment.getContent());
//            jsonObject.put("commentStar", comment.getStar());
//            data.add(jsonObject);
//        }
//        return new Result(data);
//    }

    /**
     * 删除商品
     * @param commodityID
     * @param sellerID
     * @return
     */
    public Result removeCommodity(Long commodityID, String sellerID){
        if (commodityDao.deleteOne(commodityID, sellerID) != 1){
            return new Result("下架商品失败");
        }
        return new Result();
    }

    public Result getAllCommodities(String baseURL){
        List<Commodity> commodityList = commodityDao.selectAll();
        List<JSONObject> data = new ArrayList<>();
        for (Commodity commodity : commodityList){
            JSONObject jsonObject = new JSONObject();
            commodity.setPicture(baseURL + pictureAccessDir + commodity.getPicture());
            jsonObject.put("commodityPicture", commodity.getPicture());
            jsonObject.put("sellerName", commodity.getSeller().getName());
            jsonObject.put("commodityID", commodity.getId());
            jsonObject.put("commodityType", commodity.getType());
            jsonObject.put("commodityPrice", commodity.getPrice());
            jsonObject.put("commodityName", commodity.getName());
            data.add(jsonObject);
        }
        return new Result(data);
    }

    public Result removeCommodity(Long commodityID){
        if (commodityDao.adminDeleteOne(commodityID) != 1){
            return new Result("下架商品失败");
        }
        return new Result();
    }
}
