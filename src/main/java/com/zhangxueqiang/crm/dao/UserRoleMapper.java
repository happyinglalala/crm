package com.zhangxueqiang.crm.dao;

import com.zhangxueqiang.crm.base.BaseMapper;
import com.zhangxueqiang.crm.vo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {

    //根据用户Id查询用户角色记录
    Integer countUserRoleByUserId(Integer userId);

//    根据用户Id删除用户角色
    Integer deleteUserRoleByUserId(Integer userId);

}