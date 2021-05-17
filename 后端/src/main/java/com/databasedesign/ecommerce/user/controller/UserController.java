package com.databasedesign.ecommerce.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.databasedesign.ecommerce.user.model.User;
import com.databasedesign.ecommerce.user.service.UserService;
import com.databasedesign.ecommerce.utils.CodeUtils;
import com.databasedesign.ecommerce.utils.Result;
import com.databasedesign.ecommerce.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(tags = "用户模块API")
@RestController
public class UserController{

    @Autowired
    private UserService service;

    /**
     * 发送注册验证码
     * @param request
     * @param mailbox 邮箱地址
     * @return
     */
    @ApiOperation("发送邮箱注册验证码")
    @GetMapping("/registerCode")
    public Result sendRegisterCode(HttpServletRequest request, @RequestParam("mailbox") String mailbox) {
        //检查邮箱格式
        String regEx = "^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(mailbox);
        if (!matcher.find()){
            return new Result("无效邮箱");
        }
        return service.sendRegisterCode(request, mailbox);
    }

    /**
     * 用户注册
     * @param request
     * @param inputData
     * @return
     */
    @ApiOperation(value = "注册", notes = "Models中的User所有属性(必须的5个属性要有) + code(验证码)")
    @PostMapping("/register")
    public Result register(HttpServletRequest request, @RequestBody JSONObject inputData){
        Collection<String> paramNeeded = new HashSet<String>(){{
            add("code");
            add("studentID");
            add("mailbox");
            add("password");
            add("name");
            add("sex");
        }};
        if (!inputData.keySet().containsAll(paramNeeded)){
            return new Result("参数错误");
        }
        HttpSession session = request.getSession();
        Long codeTime = (Long) session.getAttribute("codeTime");
        if (codeTime == null){
            return new Result("验证码失效");
        }
        if (!session.getAttribute(CodeUtils.REGISTER_CODE).toString().equals(inputData.getString("code"))){
            return new Result("验证码错误");
        }
        if (!session.getAttribute("mailbox").toString().equals(inputData.getString("mailbox"))){
            return new Result("邮箱不匹配");
        }
        return service.register(new User(inputData));
    }

    /**
     * 更新用户头像
     * @param avatar
     * @param request
     * @return
     */
    @ApiOperation(value = "上传及更新头像", notes = "用FormData的形式上传文件")
    @PutMapping("/avatar")
    public Result uploadAvatar(HttpServletRequest request,
                               @RequestParam("avatar") MultipartFile avatar){
        if (avatar.isEmpty() || !avatar.getContentType().contains("image")){
            return new Result("请上传图片");
        }
        return service.uploadAvatar(avatar, (String) request.getSession().getAttribute("studentID"));
    }

    /**
     * 登陆
     * @param request
     * @param inputData
     */
    @ApiOperation(value = "登陆", notes = "studentID + password")
    @PostMapping("/login")
    public Result login(HttpServletRequest request, @RequestBody JSONObject inputData) {
        //获取学号和密码
        Collection<String> paramNeeded = new HashSet<String>(){{
            add("studentID");
            add("password");
        }};
        if (!inputData.keySet().containsAll(paramNeeded)){
            return new Result("参数错误");
        }
        String studentID = inputData.getString("studentID");
        String password = inputData.getString("password");
        User user = service.login(studentID, password);
        //验证学号和密码是否正确
        if (user == null){
            return new Result("账号或密码错误");
        }
        //保存学号和姓名到session中
        HttpSession session = request.getSession();
        session.setAttribute("studentID", user.getStudentID());
        return new Result();
    }

    /**
     * 获取用户信息
     */
    @ApiOperation("获取用户信息")
    @GetMapping("/user")
    public Result getUser(HttpServletRequest request){
        String baseURL = WebUtils.getBaseURL(request);
        User user = service.getUser(baseURL, (String) request.getSession().getAttribute("studentID"));
        return new Result(user);
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @ApiOperation(value = "更新用户信息", notes = "studentID,mailbox,avatar不做更新,可以不添加在JSON中")
    @PutMapping("/user")
    public Result updateUser(HttpServletRequest request, @RequestBody User user){
        String studentID = (String) request.getSession().getAttribute("studentID");
        user.setStudentID(studentID);
        return service.editUser(user);
    }

    @GetMapping("/comments")
    public Result getComments(@RequestParam("sellerID") String sellerID){
        return service.getComments(sellerID);
    }

}
