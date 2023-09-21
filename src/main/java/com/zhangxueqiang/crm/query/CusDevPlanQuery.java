package com.zhangxueqiang.crm.query;

import com.zhangxueqiang.crm.base.BaseQuery;

/**
 * ClassName: CusDevPlanQuery
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/12 - 11:27
 */
public class CusDevPlanQuery extends BaseQuery {
    private Integer saleChanceId;//营销机会的主键

    public Integer getSaleChanceId() {
        return saleChanceId;
    }

    public void setSaleChanceId(Integer saleChanceId) {
        this.saleChanceId = saleChanceId;
    }
}
