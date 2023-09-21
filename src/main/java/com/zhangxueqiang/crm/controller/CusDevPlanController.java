package com.zhangxueqiang.crm.controller;

import com.zhangxueqiang.crm.base.BaseController;
import com.zhangxueqiang.crm.base.ResultInfo;
import com.zhangxueqiang.crm.enums.StateStatus;
import com.zhangxueqiang.crm.query.CusDevPlanQuery;
import com.zhangxueqiang.crm.query.SaleChanceQuery;
import com.zhangxueqiang.crm.service.CusDevPlanService;
import com.zhangxueqiang.crm.service.SaleChanceService;
import com.zhangxueqiang.crm.utils.LoginUserUtil;
import com.zhangxueqiang.crm.vo.CusDevPlan;
import com.zhangxueqiang.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * ClassName: CusDevPlanController
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/12 - 9:53
 */
@RequestMapping("cus_dev_plan")
@Controller
public class CusDevPlanController extends BaseController{

    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private CusDevPlanService cusDevPlanService;

    @RequestMapping("index")
    public String index(){
        return "cusDevPlan/cus_dev_plan";
    }
    @RequestMapping("toCusDevPlanPage")
    public String toCusDevPlanPage(Integer id, HttpServletRequest httpServletRequest){
        SaleChance saleChance = saleChanceService.selectByPrimaryKey(id);
        httpServletRequest.setAttribute("saleChance",saleChance);
        return "cusDevPlan/cus_dev_plan_data";
    }

    //    营销机会数据查询（分页多条件查询）
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> querySaleChanceByParams(CusDevPlanQuery cusDevPlanQuery){
        return cusDevPlanService.queryCusDevPlanByParams(cusDevPlanQuery);
    }

    @RequestMapping("add")
    @ResponseBody
    public ResultInfo addCusDevPlan(CusDevPlan cusDevPlan){
//        System.out.println("执行这行代码了吗？？？");
        cusDevPlanService.addCusDevPlan(cusDevPlan);
        return success("计划项添加成功！！！");
    }



    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCusDevPlan(CusDevPlan cusDevPlan){
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        return success("计划项更新成功！！！");
    }

//    进入添加或修改计划项的页面
    @RequestMapping("toAddOrUpdateCusDevPlanPage")
    public String toAddOrUpdateCusDevPlanPage(HttpServletRequest httpServletRequest,Integer sId,Integer id){
        httpServletRequest.setAttribute("sId",sId);
        CusDevPlan cusDevPlan = cusDevPlanService.selectByPrimaryKey(id);
        httpServletRequest.setAttribute("cusDevPlan",cusDevPlan);
        return "cusDevPlan/add_update";
    }

    @PostMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer id){
        cusDevPlanService.delete(id);
        return success("删除成功");
    }


}
