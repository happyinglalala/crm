package com.zhangxueqiang.crm.controller;

import com.zhangxueqiang.crm.base.BaseController;
import com.zhangxueqiang.crm.base.ResultInfo;
import com.zhangxueqiang.crm.model.TreeModel;
import com.zhangxueqiang.crm.service.ModuleService;
import com.zhangxueqiang.crm.vo.Module;
import org.apache.coyote.RequestInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ModuleController
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/14 - 17:20
 */
@Controller
@RequestMapping("model")
public class ModuleController extends BaseController {
    @Resource
    private ModuleService moduleService;

    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<TreeModel> queryAllModules(Integer roleId){
        return moduleService.queryAllModules(roleId);
    }

//    进入授权页面
    @RequestMapping("toAddGrantPage")
    public String toAddGrantPage(Integer roleId, HttpServletRequest httpServletRequest){
        httpServletRequest.setAttribute("roleId",roleId);
        return "role/grant";
    }

//    查询资源列表
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryModuleList(){
        return moduleService.queryModuleList();
    }


    @RequestMapping("index")
    public String index(){
        return "module/module";
    }

    @RequestMapping("add")
    @ResponseBody
    public ResultInfo add(Module module){
        moduleService.addMudule(module);
        return success("添加成功");
    }

    @RequestMapping("toAddModulePage")
    public String toAddModulePage(Integer grade,Integer parentId,HttpServletRequest httpServletRequest){
        httpServletRequest.setAttribute("grade",grade);
        httpServletRequest.setAttribute("parentId",parentId);
        return "module/add";
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(Module module){
        moduleService.update(module);
        return success("更新成功！！！");
    }

    @RequestMapping("toUpdateModulePage")
    public String toUpdateModulePage(Integer id,HttpServletRequest httpServletRequest){
//        httpServletRequest.setAttribute("id",id);
        Module module = moduleService.selectByPrimaryKey(id);
        httpServletRequest.setAttribute("module",module);
        return "module/update";
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer roleId){
        moduleService.deleteModuleById(roleId);
        return success("删除成功！！！");
    }

}
