package com.pw.example.demo6.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Classname Result
 * @Description TODO
 * @Date 2019/10/9 0009 下午 6:33
 * @Created by albert
 */
public class Result<T> {
    @JSONField(
            name = "ErrCode"
    )
    private Integer ErrCode;
    @JSONField(
            name = "Reason"
    )
    private String Reason;
    @JSONField(
            name = "Result"
    )
    private Integer Result;
    private T data;

    public Result() {
    }

    public static <T> Result<T> failResult(int code, String failMsg) {
        Result<T> result = new Result();
        result.setErrCode(Integer.valueOf(code));
        result.setResult(Integer.valueOf(0));
        result.setReason(failMsg);
        return result;
    }

    public static <T> Result<T> failResult(String failMsg) {
        return failResult(0, failMsg);
    }

    public static <T> Result<T> failResult() {
        return failResult("server fails");
    }

    public static <T> Result<T> failFallback() {
        return failResult(-1, "execute fail back method");
    }

    public static <T> Result<T> succeedResult(T data, Integer code) {
        Result<T> result = new Result();
        result.setErrCode(code);
        result.setResult(Integer.valueOf(1));
        result.setData(data);
        return result;
    }

    public static <T> Result<T> succeedResult(T data) {
        return succeedResult(data, null);
    }

    public static <T> Result<T> succeedResult() {
        return succeedResult(null, null);
    }

    public boolean resultSuccess() {
        return this.Result != null && this.Result.intValue() == 1;
    }

    public Integer getErrCode() {
        return this.ErrCode;
    }

    public void setErrCode(Integer errCode) {
        this.ErrCode = errCode;
    }

    public String getReason() {
        return this.Reason;
    }

    public void setReason(String reason) {
        this.Reason = reason;
    }

    public Integer getResult() {
        return this.Result;
    }

    public void setResult(Integer result) {
        this.Result = result;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
