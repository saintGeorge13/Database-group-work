package com.databasedesign.ecommerce.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * 验证码生成类
 */
@Component
public class CodeUtils {

    public static final String REGISTER_CODE = "registerCode";

    public static final String REGISTER_HINT = "验证码(注册)(5分钟有效): ";

    public static final String FIND_CODE = "findCode";

    public static final String FIND_HINT = "验证码(找回密码)(5分钟有效): ";

    @Autowired
    private MailUtils mailUtils;

    /**
     * 随机生成验证码
     * @return
     */
    private static String generateCode(){
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++){
            code += random.nextInt(10);
        }
        return code;
    }

    /**
     * 发送验证码
     * @param request
     * @param mailbox
     * @param codeName
     * @param hint
     * @return
     * @throws MessagingException
     */
    public Result sendCode(HttpServletRequest request,String mailbox, String codeName, String hint) throws MessagingException {
        HttpSession session = request.getSession();
        //获取上次请求验证码时间
        Long codeTime = (Long) session.getAttribute("codeTime");
        //请求间隔判断处理
        if (codeTime != null) {
            if ((new Date().getTime() - codeTime) / 60000 < 1){
                return new Result("请求验证码过于频繁");
            }
        }
        //生成验证码
        String code = generateCode();
        session.setAttribute("mailbox", mailbox);
        session.setAttribute(codeName, code);
        session.setAttribute("codeTime", new Date().getTime());
        //发送验证邮件
        mailUtils.sendVerificationMail(mailbox, hint, code);
        return new Result();
    }
}
