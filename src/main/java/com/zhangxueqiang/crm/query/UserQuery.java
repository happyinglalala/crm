package com.zhangxueqiang.crm.query;

import com.zhangxueqiang.crm.base.BaseQuery;

/**
 * ClassName: UserQuery
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/12 - 21:30
 */
public class UserQuery extends BaseQuery {
    private String userName;//用户姓名
    private String email;//邮箱
    private String phone;//手机号码

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
