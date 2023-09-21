package com.zhangxueqiang.crm.controller;

import com.zhangxueqiang.crm.base.BaseController;
import com.zhangxueqiang.crm.base.ResultInfo;
import com.zhangxueqiang.crm.query.CustomerQuery;
import com.zhangxueqiang.crm.service.CustomerService;
import com.zhangxueqiang.crm.service.OrderService;
import com.zhangxueqiang.crm.vo.Customer;
import com.zhangxueqiang.crm.vo.Details;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ClassName: CustomerController
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/16 - 10:36
 */
@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController {
    @Resource
    private CustomerService customerService;
    @Resource
    private OrderService orderService;

    @RequestMapping("index")
    public String index(){
        return "customer/customer";
    }


    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(CustomerQuery customerQuery){
        System.out.println(customerQuery);
        Map<String,Object> map= customerService.selectAllCustomerInfo(customerQuery);
        return map;
    }

    @RequestMapping("toAddOrUpdateCustomerPager")
    public String toAddOrUpdateCustomerPager(Integer id, HttpServletRequest httpServletRequest){
        if(id != null){
            Customer customer = customerService.selectByPrimaryKey(id);
            httpServletRequest.setAttribute("customer",customer);
        }
        return "customer/add_update";
    }

    @RequestMapping("add")
    @ResponseBody
    public ResultInfo add(Customer customer){
        customerService.add(customer);
        return success("添加成功！！！");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(Customer customer){
        customerService.update(customer);
        return success("修改成功！！！");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer roleId){
        customerService.deleteCustomByPrimaryKey(roleId);
        return success("删除成功！！！");
    }

    @RequestMapping("toCustomerOrderPager")
    public String toCustomerOrderPager(Integer id,HttpServletRequest httpServletRequest){
        Customer customer = customerService.selectByPrimaryKey(id);
        httpServletRequest.setAttribute("customer",customer);
//        customerService.selectOrderDetail(id);
        return "customer/customer_order";
    }

    @RequestMapping("orderDetailPage")
    public String orderDetailPage(Integer orderId){
        List<Details> details = orderService.queryDetailpage(orderId);
        return "customer/customer_order_detail";
    }

}
