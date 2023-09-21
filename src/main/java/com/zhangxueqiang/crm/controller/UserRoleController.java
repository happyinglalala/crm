package com.zhangxueqiang.crm.controller;

import com.zhangxueqiang.crm.base.BaseController;
import com.zhangxueqiang.crm.service.UserRoleService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * ClassName: UserRoleController
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/13 - 20:46
 */
@Controller
public class UserRoleController extends BaseController {
    @Resource
    private UserRoleService userRoleService;

}
