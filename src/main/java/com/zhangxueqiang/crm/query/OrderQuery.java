package com.zhangxueqiang.crm.query;

import com.zhangxueqiang.crm.base.BaseQuery;

/**
 * ClassName: OrderQuery
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/16 - 16:56
 */
public class OrderQuery extends BaseQuery {
    private Integer cusId;

    public Integer getCusId() {
        return cusId;
    }

    public void setCusId(Integer cusId) {
        this.cusId = cusId;
    }
}
