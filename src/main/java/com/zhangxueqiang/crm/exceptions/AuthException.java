package com.zhangxueqiang.crm.exceptions;

/**
 * ClassName: AuthException
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/15 - 11:39
 */
public class AuthException extends RuntimeException{
    private Integer code = 350;
    private String msg="暂无权限！！！";

    public AuthException() {
        super("暂无权限!");
    }

    public AuthException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public AuthException(Integer code) {
        super("参数异常!");
        this.code = code;
    }

    public AuthException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
