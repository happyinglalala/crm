package com.zhangxueqiang.crm.query;

import com.zhangxueqiang.crm.base.BaseQuery;

/**
 * ClassName: SaleChanceQuery
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/9 - 9:52
 */
public class SaleChanceQuery extends BaseQuery {
//    营销机会查询
//    通过继承得到的分页参数
//    客户名
    private String customerName;
//    创建人
    private String createMan;
//    分配状态  0未分配    1已分配
    private Integer state;
//    客户开发计划查询
    private String devResult;//开发状态
    private String assignMan;//指派人


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDevResult() {
        return devResult;
    }

    public void setDevResult(String devResult) {
        this.devResult = devResult;
    }

    public String getAssignMan() {
        return assignMan;
    }

    public void setAssignMan(String assignMan) {
        this.assignMan = assignMan;
    }
}
