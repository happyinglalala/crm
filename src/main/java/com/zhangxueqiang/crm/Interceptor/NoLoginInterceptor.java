package com.zhangxueqiang.crm.Interceptor;

import com.zhangxueqiang.crm.dao.UserMapper;
import com.zhangxueqiang.crm.exceptions.NoLoginException;
import com.zhangxueqiang.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: NoLoginInterceptor
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/8 - 19:51
 */
public class NoLoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        int userid = LoginUserUtil.releaseUserIdFromCookie(request);
        if(0 == userid || userMapper.selectByPrimaryKey(userid) == null){
            throw new NoLoginException();
        }
        return true;
    }
}
