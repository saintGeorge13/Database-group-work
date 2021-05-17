package com.databasedesign.ecommerce.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class MyExceptionHandler {

    /**
     * 上传文件过大异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result exceptionHandler(MaxUploadSizeExceededException e){
        return new Result("文件过大");
    }
}
