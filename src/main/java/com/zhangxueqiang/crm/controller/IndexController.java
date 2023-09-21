package com.zhangxueqiang.crm.controller;

import com.zhangxueqiang.crm.annotation.RequiredPermission;
import com.zhangxueqiang.crm.base.BaseController;
import com.zhangxueqiang.crm.dao.PermissionMapper;
import com.zhangxueqiang.crm.service.PermissionService;
import com.zhangxueqiang.crm.service.UserService;
import com.zhangxueqiang.crm.utils.LoginUserUtil;
import com.zhangxueqiang.crm.vo.User;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName: IndexController
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/7 - 20:37
 */
@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private PermissionService permissionService;

    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }

    @RequestMapping("main")
    public String main(HttpServletRequest request){
//        获取cookie中用户Id的值
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);

        User user = userService.selectByPrimaryKey(userId);

        request.getSession().setAttribute("users",user);

//        通过当前登录用户的Idf查询当前登录用户的资源列表
        List<String> permissions = permissionService.queryUserHasRoleHasPermissionByUserId(user.getId());
        System.out.println(permissions);
        request.getSession().setAttribute("permissions",permissions);
        return "main";
    }
}
