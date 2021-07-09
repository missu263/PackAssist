package com.piaoquantv.bean;

/**
 * Create by nieqi on 2021/7/8
 */
public class FeishuAccessToken {

    private int code;
    private int expire;
    private String msg;
    private String tenant_access_token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTenant_access_token() {
        return tenant_access_token;
    }

    public void setTenant_access_token(String tenant_access_token) {
        this.tenant_access_token = tenant_access_token;
    }

    @Override
    public String toString() {
        return "FeishuAccessToken{" +
                "code=" + code +
                ", expire=" + expire +
                ", msg='" + msg + '\'' +
                ", tenant_access_token='" + tenant_access_token + '\'' +
                '}';
    }
}
