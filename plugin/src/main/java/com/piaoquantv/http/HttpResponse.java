package com.piaoquantv.http;


/**
 * Create by nieqi on 2021/7/8
 */
public class HttpResponse<T> {

    private int code;
    private String message;
    private String msg;
    private T data;

    public boolean isSuccessful() {
        return code == 0;
    }

    public HttpResponse() {
    }

    public String getRealMessage() {
        return message == null ? msg : message;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
