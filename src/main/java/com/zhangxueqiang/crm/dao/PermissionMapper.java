package com.zhangxueqiang.crm.dao;

import com.zhangxueqiang.crm.base.BaseMapper;
import com.zhangxueqiang.crm.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {


    Integer selectModuleByRoleId(Integer roleId);

    Integer deleteByRoleId(Integer roleId);

    List<String> queryUserHasRoleHasPermissionByUserId(Integer id);

    Integer countPermissionByModuleId(Integer roleId);

    Integer deletePermissionByModuleId(Integer roleId);

    /*List<Integer> queryRoleHasModelIdByRoleId(Integer roleId);*/
}