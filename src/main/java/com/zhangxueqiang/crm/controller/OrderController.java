package com.zhangxueqiang.crm.controller;

import com.zhangxueqiang.crm.base.BaseController;
import com.zhangxueqiang.crm.query.OrderQuery;
import com.zhangxueqiang.crm.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * ClassName: OrderController
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/16 - 16:28
 */
@Controller
@RequestMapping("order")
public class OrderController extends BaseController {
    @Resource
    private OrderService orderService;

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(OrderQuery orderQuery){
        return orderService.queryAllList(orderQuery);
    }
}
