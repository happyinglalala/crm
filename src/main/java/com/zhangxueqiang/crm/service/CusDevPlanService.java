package com.zhangxueqiang.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangxueqiang.crm.base.BaseService;
import com.zhangxueqiang.crm.dao.CusDevPlanMapper;
import com.zhangxueqiang.crm.dao.SaleChanceMapper;
import com.zhangxueqiang.crm.query.CusDevPlanQuery;
import com.zhangxueqiang.crm.query.SaleChanceQuery;
import com.zhangxueqiang.crm.utils.AssertUtil;
import com.zhangxueqiang.crm.vo.CusDevPlan;
import com.zhangxueqiang.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: CusDevPlanService
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/12 - 11:23
 */
@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {
    @Resource
    private CusDevPlanMapper cusDevPlanMapper;

    @Resource
    private SaleChanceMapper saleChanceMapper;

    //多条件查询客户开发计划
    public Map<String,Object> queryCusDevPlanByParams(CusDevPlanQuery cusDevPlanQuery){
        Map<String,Object> map = new HashMap<>();
//        开启分页
        PageHelper.startPage(cusDevPlanQuery.getPage(),cusDevPlanQuery.getLimit());
//        得到对应的分页对象
        PageInfo<CusDevPlan> pageInfo = new PageInfo<>(cusDevPlanMapper.selectByParams(cusDevPlanQuery));
//        按照Layui的格式返回对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addCusDevPlan(CusDevPlan cusDevPlan){
        AssertUtil.isTrue(cusDevPlan.getSaleChanceId()==null||saleChanceMapper.selectByPrimaryKey(cusDevPlan.getSaleChanceId())==null,"数据异常请重试！！！");
        AssertUtil.isTrue(StringUtils.isBlank(cusDevPlan.getPlanItem()),"计划项内容不能为空！！！");
        AssertUtil.isTrue(cusDevPlan.getPlanDate()==null,"计划时间不能为空！！！");
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.insertSelective(cusDevPlan)!=1,"计划向数据添加失败！！！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCusDevPlan(CusDevPlan cusDevPlan){
        AssertUtil.isTrue(cusDevPlan.getId()==null||cusDevPlanMapper.selectByPrimaryKey(cusDevPlan.getId())==null,"系统异常请重试！！！");
        AssertUtil.isTrue(cusDevPlan.getSaleChanceId()==null||saleChanceMapper.selectByPrimaryKey(cusDevPlan.getSaleChanceId())==null,"数据异常请重试！！！");
        AssertUtil.isTrue(StringUtils.isBlank(cusDevPlan.getPlanItem()),"计划项内容不能为空！！！");
        AssertUtil.isTrue(cusDevPlan.getPlanDate()==null,"计划时间不能为空！！！");
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan)!=1,"计划项更新失败！！！");
    }


    public void delete(Integer id) {
        AssertUtil.isTrue(id==null,"系统错误！！！请重试！！！");
//        AssertUtil.isTrue(cusDevPlanMapper.deleteByPrimaryKey(id)!=1,"删除失败！！！");
        CusDevPlan cusDevPlan = cusDevPlanMapper.selectByPrimaryKey(id);
        cusDevPlan.setIsValid(0);
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan)!=1,"计划项数据删除失败！！！");
    }

}
