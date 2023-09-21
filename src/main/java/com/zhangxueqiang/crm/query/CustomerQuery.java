package com.zhangxueqiang.crm.query;

import com.zhangxueqiang.crm.base.BaseQuery;

/**
 * ClassName: CustomerQuery
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/16 - 10:38
 */
public class CustomerQuery extends BaseQuery {

    private String name;

    private String khno;

    private String level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKhno() {
        return khno;
    }

    public void setKhno(String khno) {
        this.khno = khno;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "CustomerQuery{" +
                "name='" + name + '\'' +
                ", khno='" + khno + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
