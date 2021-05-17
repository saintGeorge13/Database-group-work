package com.databasedesign.ecommerce.commodity.controller;

import com.alibaba.fastjson.JSONObject;
import com.databasedesign.ecommerce.commodity.model.Commodity;
import com.databasedesign.ecommerce.commodity.service.CommodityService;
import com.databasedesign.ecommerce.user.model.User;
import com.databasedesign.ecommerce.utils.Result;
import com.databasedesign.ecommerce.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    @GetMapping("/commodity_types")
    public Result getCommodityTypes(){
        return commodityService.getCommodityTypes();
    }

    @PostMapping("/commodities")
    public Result addCommodity(HttpServletRequest request,
                               @RequestParam("picture") MultipartFile picture){
        Collection<String> paramNeeded = new HashSet<String>(){{
            add("name");
            add("type");
            add("price");
            add("expressFee");
            add("description");
        }};
        if (!request.getParameterMap().keySet().containsAll(paramNeeded)){
            return new Result("参数错误");
        }
        if (picture.isEmpty() || !picture.getContentType().contains("image")){
            return new Result("请上传图片");
        }
        Commodity commodity = new Commodity();
        try {
            commodity.setPrice(Double.parseDouble(request.getParameter("price")));
            commodity.setExpressFee(Double.parseDouble(request.getParameter("expressFee")));
        } catch (NumberFormatException e) {
            return new Result("价格和邮费填写数字");
        }
        commodity.setSeller(new User((String) request.getSession().getAttribute("studentID")));
        commodity.setName(request.getParameter("name"));
        commodity.setType(request.getParameter("type"));
        commodity.setDescription(request.getParameter("description"));
        return commodityService.addCommodity(commodity, picture);
    }

    @GetMapping("/random_commodities")
    public Result getRandomCommodities(HttpServletRequest request){
        String baseURL = WebUtils.getBaseURL(request);
        return commodityService.getRandomCommodities(baseURL);
    }

    @GetMapping("/commodities")
    public Result getCommoditiesByKeyword(HttpServletRequest request, @RequestParam("keyword") String keyword){
        String baseURL = WebUtils.getBaseURL(request);
        return commodityService.getCommoditiesByKeyword(baseURL, keyword);
    }

    @GetMapping("/my_commodities")
    public Result getCommoditiesBySellerID(HttpServletRequest request){
        String baseURL = WebUtils.getBaseURL(request);
        String sellerID = (String) request.getSession().getAttribute("studentID");
        return commodityService.getCommoditiesBySellerID(baseURL, sellerID);
    }

    @GetMapping("/commodities/{id}")
    public Result getCommodityByID(HttpServletRequest request, @PathVariable("id") Long id){
        String baseURL = WebUtils.getBaseURL(request);
        return commodityService.getCommodityByID(baseURL, id);
    }

//    @GetMapping("/comments")
//    public Result getCommentsByID(@RequestParam("commodityID") Long commodityID){
//        return commodityService.getCommentsByID(commodityID);
//    }

    @DeleteMapping("/commodities/{id}")
    public Result removeCommodity(HttpServletRequest request, @PathVariable("id") Long id){
        String sellerID = (String) request.getSession().getAttribute("studentID");
        return commodityService.removeCommodity(id, sellerID);
    }
}
