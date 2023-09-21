package com.zhangxueqiang.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangxueqiang.crm.base.BaseService;
import com.zhangxueqiang.crm.dao.OrderMapper;
import com.zhangxueqiang.crm.query.OrderQuery;
import com.zhangxueqiang.crm.vo.Details;
import com.zhangxueqiang.crm.vo.Order;
import com.zhangxueqiang.crm.vo.SaleChance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: OrderService
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/16 - 16:26
 */
@Service
public class OrderService extends BaseService<Order,Integer> {
    @Resource
    private OrderMapper orderMapper;


    public Map<String, Object> queryAllList(OrderQuery orderQuery) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(orderQuery.getPage(),orderQuery.getLimit());
        PageInfo<Order> pageInfo = new PageInfo<>(orderMapper.selectByParams(orderQuery));
//        按照Layui的格式返回对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    public List<Details> queryDetailpage(Integer orderId) {
        return orderMapper.queryDetailpage(orderId);
    }
}
