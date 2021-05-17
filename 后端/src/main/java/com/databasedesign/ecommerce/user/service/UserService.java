package com.databasedesign.ecommerce.user.service;

import com.alibaba.fastjson.JSONObject;
import com.databasedesign.ecommerce.commodity.model.Commodity;
import com.databasedesign.ecommerce.order.dao.CommentDao;
import com.databasedesign.ecommerce.order.model.Comment;
import com.databasedesign.ecommerce.order.model.Order;
import com.databasedesign.ecommerce.user.dao.UserDao;
import com.databasedesign.ecommerce.user.model.User;
import com.databasedesign.ecommerce.user.utils.UserUtils;
import com.databasedesign.ecommerce.utils.CodeUtils;
import com.databasedesign.ecommerce.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户服务层
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private CodeUtils codeUtils;

    @Value("${file.avatar.uploadPath}")
    private String avatarDir;

    @Value("${file.avatar.staticAccessPath}")
    private String avatarAccessDir;

    /**
     * 发送注册邮箱验证码
     * @param request
     * @param mailbox
     * @return
     */
    @Transactional
    public Result sendRegisterCode(HttpServletRequest request, String mailbox) {
        User user = userDao.selectByMailbox(mailbox);
        if (user != null){
            return new Result("邮箱已经被注册");
        }
        try {
            return codeUtils.sendCode(request, mailbox, CodeUtils.REGISTER_CODE, CodeUtils.REGISTER_HINT);
        } catch (MessagingException e) {
            return new Result("发送验证码失败");
        }
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @Transactional
    public Result register(User user){
        Result check = UserUtils.checkUser(user);
        if (check.getCode() != 0){
            return check;
        }
        if (userDao.insert(user) != 1){
            return new Result("注册失败");
        }
        return new Result();
    }

    /**
     * 上传头像
     * @param avatar
     * @param studentID
     * @return
     */
    @Transactional
    public Result uploadAvatar(MultipartFile avatar, String studentID){
        String fileName = studentID + ".jpg";
        File file = new File(avatarDir + fileName);
        try {
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            out.write(avatar.getBytes());
            out.close();
        } catch (FileNotFoundException e) {
            return new Result("上传失败");
        } catch (IOException e) {
            return new Result("上传失败");
        }
        userDao.updateAvatarByID(fileName, studentID);
        return new Result();
    }

    /**
     * 登陆
     * @param studentID
     * @param password
     * @return
     */
    @Transactional
    public User login(String studentID, String password){
        return userDao.selectByIDAndPassword(studentID, password);
    }

    /**
     * 获取用户信息
     * @param baseURL
     * @param studentID
     * @return
     */
    @Transactional
    public User getUser(String baseURL, String studentID){
        User user = userDao.selectByID(studentID);
        user.setAvatar(baseURL + avatarAccessDir + user.getAvatar());
        return user;
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public Result editUser(User user){
        Result check = UserUtils.checkUser(user);
        if (check.getCode() != 0){
            return check;
        }
        if (userDao.update(user) != 1){
            return new Result("更新失败");
        }
        return new Result();
    }

    /**
     * 获取商家评论
     * @param seller_id
     * @return
     */
    public Result getComments(String seller_id){
        List<Comment> commentList = commentDao.selectBySellerID(seller_id);
        List<JSONObject> data = new ArrayList<>();
        for (Comment comment : commentList){
            JSONObject jsonObject = new JSONObject();
            Order order = comment.getOrder();
            Commodity commodity = order.getCommodity();
            User customer = order.getCustomer();
            jsonObject.put("content", comment.getContent());
            jsonObject.put("star", comment.getStar());
            jsonObject.put("date", order.getDate());
            jsonObject.put("commodity", commodity.getName());
            jsonObject.put("customer", customer.getName());
            data.add(jsonObject);
        }
        return new Result(data);
    }
}
