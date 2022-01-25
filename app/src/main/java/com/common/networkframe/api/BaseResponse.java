package com.common.networkframe.api;

import com.google.gson.annotations.SerializedName;

/**
 * @author lst
 * @Description: 接口响应基类，所有的接口返回数据继承该类
 * @date 2021/6/21
 */
public class BaseResponse {

    @SerializedName("code")
    private int code;
    @SerializedName("errorMsg")
    private String errorMsg;
    @SerializedName("cache")
    private int cache;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getCache() {
        return cache;
    }

    public void setCache(int cache) {
        this.cache = cache;
    }

}
