package com.zhangxueqiang.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangxueqiang.crm.base.BaseService;
import com.zhangxueqiang.crm.dao.SaleChanceMapper;
import com.zhangxueqiang.crm.enums.DevResult;
import com.zhangxueqiang.crm.enums.StateStatus;
import com.zhangxueqiang.crm.query.SaleChanceQuery;
import com.zhangxueqiang.crm.utils.AssertUtil;
import com.zhangxueqiang.crm.utils.PhoneUtil;
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
 * ClassName: SaleChanceService
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/9 - 9:41
 */
@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {
    @Resource
    private SaleChanceMapper saleChanceMapper;

    /**
     * 多条件分页查询营销机会
     */
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery){
        Map<String,Object> map = new HashMap<>();
//        开启分页
        PageHelper.startPage(saleChanceQuery.getPage(),saleChanceQuery.getLimit());
//        得到对应的分页对象
        PageInfo<SaleChance> pageInfo = new PageInfo<>(saleChanceMapper.selectByParams(saleChanceQuery));
//        按照Layui的格式返回对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    /**
     * 添加营销机会
     *  参数校验
     *      customerName    非空
     *      linkMan         非空
     *      linkPhone       非空格式正确
     *  设置相关参数默认值
     *      createMan       当前登录用户
     *      assignMan
     *          如果没有设置指派人
     *              state分配状态0
     *              指派时间assignTime null
     *              开发状态    0未开发
     *          如果设置了指派人
     *              state分配状态1
     *              指派时间assignTime 当前时间
     *              开发状态    1开发中
     *      isValid是否有效
     *          有效
     *      createDate创建时间
     *          系统当前时间
     *      updateDate更新时间
     *          系统当前时间
     *   执行添加操作，判断受影响的行数
     *
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addSaleChance(SaleChance saleChance){
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getCustomerName()),"客户名称不能为空！！！");
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getLinkMan()),"联系人不能为空！！！");
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getLinkPhone()),"联系电话不能为空！！！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(saleChance.getLinkPhone()),"号码格式错误！！！");
//        isValid是否有效   有效
        saleChance.setIsValid(1);
//        系统当前时间
        saleChance.setCreateDate(new Date());
//        系统当前时间
        saleChance.setUpdateDate(new Date());
        if(StringUtils.isBlank(saleChance.getAssignMan())){
            /*如果没有设置指派人
              state分配状态0
              指派时间assignTime null
              开发状态    0未开发*/
            saleChance.setState(StateStatus.UNSTATE.getType());
            saleChance.setAssignTime(null);
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
        }else {
            /*如果设置了指派人
              state分配状态1
              指派时间assignTime 当前时间
              开发状态    1开发中*/
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setAssignTime(new Date());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
        }
        AssertUtil.isTrue(saleChanceMapper.insertSelective(saleChance)<1,"添加营销机会失败！！！");
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChance(SaleChance saleChance){
        AssertUtil.isTrue(null == saleChance.getId(),"待更新的记录不存在！！！");
        SaleChance selectSaleChance = saleChanceMapper.selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(null == selectSaleChance,"待更新的记录不存在！！！");
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getCustomerName()),"客户名称不能为空！！！");
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getLinkMan()),"联系人不能为空！！！");
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getLinkPhone()),"联系电话不能为空！！！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(saleChance.getLinkPhone()),"号码格式错误！！！");
//        设置相关参数的默认值
        saleChance.setUpdateDate(new Date());
//        判断原始数据是否存在
//        原始数据不存在
        if(StringUtils.isBlank(selectSaleChance.getAssignMan())){
//            输入数据存在
            if(!StringUtils.isBlank(saleChance.getAssignMan())){
//                修改指派时间
                saleChance.setAssignTime(new Date());
//                修改分配状态
                saleChance.setState(StateStatus.STATED.getType());
//                修改开发状态
                saleChance.setDevResult(DevResult.DEVING.getStatus());
            }
        }else {
//            数据存在
//            输入不存在
            if(StringUtils.isBlank(saleChance.getAssignMan())){
//                指派时间为null
                saleChance.setAssignTime(null);
//                分配状态为0
                saleChance.setState(StateStatus.UNSTATE.getType());
//                开发状态
                saleChance.setDevResult(DevResult.UNDEV.getStatus());
            }else {
                if(!selectSaleChance.equals(saleChance)){
                    saleChance.setAssignTime(new Date());
                }else {
                    saleChance.setAssignTime(selectSaleChance.getAssignTime());
                }
            }
        }
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance)<1,"更新失败！！！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSaleChance(Integer[] ids){
        AssertUtil.isTrue(ids == null||ids.length == 0,"请选择删除记录！！！");
        AssertUtil.isTrue(saleChanceMapper.deleteBatch(ids)!=ids.length,"删除失败！！！");
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChanceDevResult(Integer id, Integer devResult) {
        AssertUtil.isTrue(id==null||devResult==null,"待更新机会不存在！！！");
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(saleChance==null,"待更新机会不存在！！！");
        saleChance.setUpdateDate(new Date());
        saleChance.setDevResult(devResult);
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance)!=1,"开发状态更新失败！！");
    }


}
