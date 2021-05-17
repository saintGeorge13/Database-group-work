package com.databasedesign.ecommerce.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Component
@Aspect
public class MyAspect {

    @Pointcut("within(com.databasedesign.ecommerce.*.controller..*)")
    public void myAspect(){}

    /**
     * 验证用户是否登陆(权限拦截)
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("myAspect()")
    public Object verification(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String methodName = joinPoint.getSignature().getName();
        if (!methodName.equals("sendRegisterCode") && !methodName.equals("register") &&
                !methodName.equals("login")){
            HttpSession session = request.getSession();
            if (session.getAttribute("studentID") == null && session.getAttribute("admin") == null){
                return new Result("先登录");
            }
        }
        return joinPoint.proceed();
    }
}
