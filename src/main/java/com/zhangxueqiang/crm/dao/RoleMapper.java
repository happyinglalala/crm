package com.zhangxueqiang.crm.dao;

import com.zhangxueqiang.crm.base.BaseMapper;
import com.zhangxueqiang.crm.vo.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {

    //    查询所有的角色(id,roleName)
    List<Map<String,Object>> queryAllRoles(Integer userId);


//    通过角色名查询记录
    Role queryByRoleName(String roleName);


}