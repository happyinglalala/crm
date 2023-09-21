package com.zhangxueqiang.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangxueqiang.crm.base.BaseService;
import com.zhangxueqiang.crm.dao.CustomerMapper;
import com.zhangxueqiang.crm.query.CustomerQuery;
import com.zhangxueqiang.crm.utils.AssertUtil;
import com.zhangxueqiang.crm.utils.PhoneUtil;
import com.zhangxueqiang.crm.vo.Customer;
import com.zhangxueqiang.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: CustomerService
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/16 - 10:34
 */
@Service
public class CustomerService extends BaseService<Customer,Integer> {
    @Resource
    private CustomerMapper customerMapper;

    public Map<String, Object> selectAllCustomerInfo(CustomerQuery customerQuery) {
        Map<String,Object> map = new HashMap<>();
        //        开启分页
        PageHelper.startPage(customerQuery.getPage(),customerQuery.getLimit());
//        得到对应的分页对象
        PageInfo<Customer> pageInfo = new PageInfo<>(customerMapper.selectByParams(customerQuery));
//        按照Layui的格式返回对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());


        return map;
    }

    public void add(Customer customer) {
//        参数校验
//        客户名称  非空  名称唯一
//        法人代表  非空
//        手机号码  非空  格式正确
        checkParam(customer.getName(),customer.getFr(),customer.getPhone());
//        设置默认值
//        有效
        customer.setIsValid(1);
//        创建时间
        customer.setCreateDate(new Date());
//        修改时间
        customer.setUpdateDate(new Date());
//        客户编号（系统生成 KH时间戳）
        customer.setKhno("KH"+ new Date().getTime());
//        执行添加操作
        customerMapper.insertSelective(customer);
    }

    private void checkParam(String name, String fr, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(name),"客户名称不能为空！！！");
        AssertUtil.isTrue(customerMapper.selectCustmerByName(name)!=null,"客户名称重复！！！");
        AssertUtil.isTrue(StringUtils.isBlank(fr),"法人代表不能为空！！！");
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号码不能为空！！！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"手机格式错误！！！");
    }

    public void update(Customer customer) {
//        参数校验
//        用户主键  非空  且存在
        AssertUtil.isTrue(customer.getId() == null,"用户不存在！！！");
        AssertUtil.isTrue(customerMapper.selectByPrimaryKey(customer.getId())==null,"用户不存在！！！");
//        客户名称  非空  名称唯一
//        法人代表  非空
//        手机号码  非空  格式正确
        AssertUtil.isTrue(StringUtils.isBlank(customer.getName()),"客户名称不能为空！！！");
        Customer customer1 = customerMapper.selectCustmerByName(customer.getName());
        AssertUtil.isTrue(customerMapper.selectCustmerByName(customer.getName())!=null&&!customer1.getName().equals(customer.getName()),"客户名称重复！！！");
        AssertUtil.isTrue(StringUtils.isBlank(customer.getFr()),"法人代表不能为空！！！");
        AssertUtil.isTrue(StringUtils.isBlank(customer.getPhone()),"手机号码不能为空！！！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(customer.getPhone()),"手机格式错误！！！");
//        设置默认值
//        有效
        customer.setIsValid(1);
//        修改时间
        customer.setUpdateDate(new Date());
//        执行更新操作
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    public void deleteCustomByPrimaryKey(Integer id) {
        AssertUtil.isTrue(id==null,"客户不存在！！！");
        System.out.println(customerMapper.selectByPrimaryKey(id)==null);
        AssertUtil.isTrue(customerMapper.selectByPrimaryKey(id)==null,"客户不存在");
        AssertUtil.isTrue(customerMapper.deleteCustomByPrimaryKey(id)<1,"删除失败！！！");
    }

    public void selectOrderDetail(Integer id) {
        AssertUtil.isTrue(id==null,"客户信息不存在！！！");
        AssertUtil.isTrue(customerMapper.selectByPrimaryKey(id)==null,"客户信息不存在！！！");
        
    }
}
