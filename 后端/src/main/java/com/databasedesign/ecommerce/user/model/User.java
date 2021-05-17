package com.databasedesign.ecommerce.user.model;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * user表实例类
 */
@ApiModel(description = "用户实例类")
public class User {

    @ApiModelProperty("不能为null")
    private String studentID;

    @ApiModelProperty("不能为null")
    private String mailbox;

    @ApiModelProperty("不能为null")
    private String password;

    private String avatar;

    @ApiModelProperty("不能为null")
    private String name;

    @ApiModelProperty("不能为null")
    private String sex;

    private String college;

    private String major;

    private String dormitory;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    @Override
    public String toString() {
        return "User{" +
                "studentID='" + studentID + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public User() {
    }

    public User(String studentID){
        this.studentID = studentID;
    }

    public User(JSONObject jsonObject) {
        this.studentID = jsonObject.getString("studentID");
        this.mailbox = jsonObject.getString("mailbox");
        this.password = jsonObject.getString("password");
        this.name = jsonObject.getString("name");
        this.sex = jsonObject.getString("sex");
        this.college = jsonObject.getString("college");
        this.major = jsonObject.getString("major");
        this.dormitory = jsonObject.getString("dormitory");
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
