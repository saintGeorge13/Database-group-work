package com.databasedesign.ecommerce.user.dao;

import com.databasedesign.ecommerce.user.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    /**
     * 通过邮箱地址获取用户信息
     * @param mailbox 邮箱地址
     * @return
     */
    public User selectByMailbox(String mailbox);

    /**
     * 添加用户
     * @param user
     * @return
     */
    public int insert(User user);

    /**
     * 通过学生ID和密码获取学生信息(验证登陆信息)
     * @param studentID 账号(学生ID)
     * @param password 密码
     * @return 账号密码都匹配的学生
     */
    public User selectByIDAndPassword(@Param("studentID") String studentID, @Param("password") String password);

    /**
     * 根据ID更新头像
     * @param avatar
     * @param studentID
     * @return
     */
    public int updateAvatarByID(@Param("avatar") String avatar, @Param("studentID") String studentID);

    /**
     * 通过学生ID获取用户信息
     * @param studentID 学生ID
     * @return 一个User实例
     */
    public User selectByID(String studentID);

    /**
     * 更新用户
     * @param user
     * @return
     */
    public int update(User user);
}
