package com.databasedesign.ecommerce.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailUtils {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送邮箱验证码
     * @param to 接收邮箱
     * @param hint 提示信息
     * @param code 验证码
     * @throws MessagingException
     */
    public void sendVerificationMail(String to, String hint, String code) throws MessagingException {
        Context context = new Context();
        context.setVariable("hint", hint);
        context.setVariable("code", code);
        String emailContent = templateEngine.process("mailTemplate.html", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage,"UTF-8");
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("数据库设计大作业");
        message.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }
}
