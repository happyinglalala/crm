package com.zhangxueqiang.crm.service;

import com.zhangxueqiang.crm.base.BaseService;
import com.zhangxueqiang.crm.dao.PermissionMapper;
import com.zhangxueqiang.crm.dao.UserRoleMapper;
import com.zhangxueqiang.crm.vo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: PermissionService
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/15 - 9:57
 */
@Service
public class PermissionService extends BaseService<Permission,Integer> {
    @Resource
    private PermissionMapper permissionMapper;

//    通过查询用户拥有的角色，角色拥有的资源，得到用户拥有的资源列表（资源权限码）
    public List<String> queryUserHasRoleHasPermissionByUserId(Integer id) {
        return permissionMapper.queryUserHasRoleHasPermissionByUserId(id);
    }
}
