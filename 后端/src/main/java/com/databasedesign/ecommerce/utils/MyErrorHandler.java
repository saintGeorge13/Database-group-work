package com.databasedesign.ecommerce.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 错误处理类
 */
@RestController
public class MyErrorHandler implements ErrorController {
    @Override
    public String getErrorPath() {
        return null;
    }

    @RequestMapping("/error")
    public Result errorHandler(HttpServletRequest request){
        Object status = request.getAttribute("javax.servlet.error.status_code");
        Object message = request.getAttribute("javax.servlet.error.message");
        Object error = HttpStatus.valueOf((Integer) status).getReasonPhrase();
        JSONObject data = new JSONObject();
        data.put("status", status);
        data.put("message", message);
        data.put("error", error);
        Result result = new Result();
        result.setCode(999);
        result.setMsg("发生错误");
        result.setData(data);
        return result;
    }
}
