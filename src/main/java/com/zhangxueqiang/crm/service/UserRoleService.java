package com.zhangxueqiang.crm.service;

import com.zhangxueqiang.crm.base.BaseService;
import com.zhangxueqiang.crm.vo.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ClassName: UserRoleService
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/13 - 20:45
 */
@Service
public class UserRoleService extends BaseService<UserRole,Integer> {
    @Resource
    private UserRoleService userRoleService;

}
