package com.zhangxueqiang.crm.query;

import com.zhangxueqiang.crm.base.BaseQuery;

/**
 * ClassName: RoleQuery
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/14 - 9:42
 */
public class RoleQuery extends BaseQuery {
//    角色姓名
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
