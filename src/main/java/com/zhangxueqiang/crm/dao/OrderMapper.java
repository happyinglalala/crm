package com.zhangxueqiang.crm.dao;

import com.zhangxueqiang.crm.base.BaseMapper;
import com.zhangxueqiang.crm.vo.Details;
import com.zhangxueqiang.crm.vo.Order;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order,Integer> {

    List<Details> queryDetailpage(Integer orderId);
}