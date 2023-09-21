package com.zhangxueqiang.crm.controller;

import com.zhangxueqiang.crm.base.BaseController;
import com.zhangxueqiang.crm.base.ResultInfo;
import com.zhangxueqiang.crm.query.RoleQuery;
import com.zhangxueqiang.crm.service.RoleService;
import com.zhangxueqiang.crm.vo.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ClassName: RoleController
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/13 - 16:10
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;

    /**
     * 查询所有的角色列表
     * @return
     */
    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String,Object>> queryAllRoles(Integer userId){
        return roleService.queryAllRoles(userId);
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> list(RoleQuery roleQuery){
        return roleService.queryByParamsForTable(roleQuery);
    }

//    进入角色管理页面
    @RequestMapping("index")
    public String index(){
        return "role/role";
    }

//    添加角色
    @PostMapping("add")
    @ResponseBody
    public ResultInfo add(Role role){
        roleService.addRole(role);
        return success("添加成功！！！");
    }

    @RequestMapping("toAddOrUpdateRole")
    public String toAddOrUpdateRole(Integer roleId, HttpServletRequest httpServletRequest){
        if(roleId!=null){
            Role role = roleService.selectByPrimaryKey(roleId);
            httpServletRequest.setAttribute("role",role);
        }
        return "role/add_update";
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo update(Role role){
        roleService.updateRole(role);
        return success("修改成功！！！");
    }

    @PutMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer roleId){
        roleService.delete(roleId);
        return success("删除成功！！！");
    }

    /**
     * 角色绑定权限
     * @param mIds 权限数组
     * @param roleId 角色Id
     * @return
     */
    @RequestMapping("addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer[] mIds,Integer roleId){
        roleService.addGrant(mIds,roleId);
        return success("角色绑定权限成功！！！");
    }


}
