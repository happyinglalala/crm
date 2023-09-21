package com.zhangxueqiang.crm.dao;

import com.zhangxueqiang.crm.base.BaseMapper;
import com.zhangxueqiang.crm.vo.Customer;

public interface CustomerMapper extends BaseMapper<Customer,Integer> {

    Customer selectCustmerByName(String name);

    int deleteCustomByPrimaryKey(Integer id);
}