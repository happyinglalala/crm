package com.zhangxueqiang.crm.model;


/**
 * ClassName: UserModel
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/8 - 9:41
 */
public class UserModel {
//    private Integer id;
    private String userIdStr;
    private String userName;
    private String trueName;

    public String getUserIdStr() {
        return userIdStr;
    }

    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userIdStr='" + userIdStr + '\'' +
                ", userName='" + userName + '\'' +
                ", trueName='" + trueName + '\'' +
                '}';
    }
}
