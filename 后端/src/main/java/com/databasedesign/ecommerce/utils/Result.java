package com.databasedesign.ecommerce.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 返回对象类
 * @param <T>
 */
@ApiModel(description = "返回结果类")
public class Result<T> {

    //错误码, 0为正确
    @ApiModelProperty("0为操作成功, -1为操作失败, 999为发生错误(可能是接口参数不对,也可能是出bug)")
    private int code;

    //错误信息
    @ApiModelProperty("提示信息")
    private String msg;

    //数据
    @ApiModelProperty("数据")
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result(){
        this.code = 0;
        this.msg = "操作成功";
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(String msg){
        this.code = -1;
        this.msg = msg;
    }
}
