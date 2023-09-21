package com.zhangxueqiang.crm.controller;

import com.zhangxueqiang.crm.annotation.RequiredPermission;
import com.zhangxueqiang.crm.base.BaseController;
import com.zhangxueqiang.crm.base.ResultInfo;
import com.zhangxueqiang.crm.enums.StateStatus;
import com.zhangxueqiang.crm.query.SaleChanceQuery;
import com.zhangxueqiang.crm.service.SaleChanceService;
import com.zhangxueqiang.crm.utils.CookieUtil;
import com.zhangxueqiang.crm.utils.LoginUserUtil;
import com.zhangxueqiang.crm.vo.SaleChance;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * ClassName: SaleChanceController
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/9 - 9:42
 */
@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {


    @Resource
    private SaleChanceService saleChanceService;

    //    营销机会数据查询（分页多条件查询）
    @RequiredPermission(code = "101001")
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery,
                                                      Integer flag,
                                                      HttpServletRequest httpServletRequest){
        if(flag != null&&flag == 1){
//            设置分配状态
            saleChanceQuery.setState(StateStatus.STATED.getType());
            int id = LoginUserUtil.releaseUserIdFromCookie(httpServletRequest);
            saleChanceQuery.setAssignMan(id+"");
        }
        return saleChanceService.querySaleChanceByParams(saleChanceQuery);
    }


    @RequiredPermission(code = "1010")
    @RequestMapping("index")
    public String index(){
        return "saleChance/sale_chance";
    }

    @RequiredPermission(code = "101002")
    @RequestMapping("add")
    @ResponseBody
    public ResultInfo addSaleChance(SaleChance saleChance, HttpServletRequest httpServletRequest){
        String userName = CookieUtil.getCookieValue(httpServletRequest, "userName");
        saleChance.setCreateMan(userName);
        saleChanceService.addSaleChance(saleChance);
        return success("营销机会添加成功！！！");
    }

//    进入/编辑
    @RequestMapping("toSaleChancePage")
    public String toSaleChancePage(Integer saleChanceId,HttpServletRequest httpServletRequest){
        if(saleChanceId != null){
            SaleChance saleChance = saleChanceService.selectByPrimaryKey(saleChanceId);
            httpServletRequest.setAttribute("saleChance",saleChance);
        }
        return "saleChance/add_update";
    }

//    更新操作
    @RequiredPermission(code = "101004")
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateSaleChance(SaleChance saleChance){
        saleChanceService.updateSaleChance(saleChance);
        return success("营销机会更新成功！！！");
    }

    @RequiredPermission(code = "101003")
    @PutMapping("delete")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids){
        saleChanceService.deleteBatch(ids);
        return success("删除成功！！！");
    }


    @PostMapping("updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(Integer id,Integer devResult){
        saleChanceService.updateSaleChanceDevResult(id,devResult);
        return success();
    }

}
