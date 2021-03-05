package com.xiaozhu.washcar.common;

/**
 * @Description:
 * @Author hans
 * @Date 2020/12/6 14:37
 * @Version 1.0
 */
public class Result<T> {
    public int code;

    private String msg;

    private T data;

    private final static String SUCCESS = "success";

    public Result<T> setCode(ResultCode retCode) {
        this.code = retCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static <T> Result<T> success() {
        return new Result<T>().setCode(ResultCode.SUCCESS).setMsg(SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>().setCode(ResultCode.SUCCESS).setMsg(SUCCESS).setData(data);
    }

    public static <T> Result<T> error(String message) {
        return new Result<T>().setCode(ResultCode.FAIL).setMsg(SUCCESS);
    }

    public static <T> Result<T> error(int code, String msg) {
        return new Result<T>().setCode(code).setMsg(msg);
    }

    public static <T> Result<T> error(int code, String msg, T data) {
        return new Result<T>().setCode(code).setMsg(msg).setData(data);
    }
}
