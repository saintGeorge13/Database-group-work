package com.databasedesign.ecommerce.user.utils;

import com.databasedesign.ecommerce.user.model.User;
import com.databasedesign.ecommerce.utils.Result;

public class UserUtils {

    /**
     * 检查用户信息是否规范
     * @param user
     * @return
     */
    public static Result checkUser(User user){
        String studentID = user.getStudentID();
        String password = user.getPassword();
        String name = user.getName();

        if (studentID.length() < 4 || studentID.length() >16){
            return new Result("学生ID要4到16位");
        }
        if (password.length() < 6 || password.length() > 16){
            return new Result("密码要6到16位");
        }
        if (name.length() > 10){
            return new Result("姓名不能超过10位");
        }
        return new Result();
    }
}
