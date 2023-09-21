package com.zhangxueqiang.crm.aspect;

import com.zhangxueqiang.crm.annotation.RequiredPermission;
import com.zhangxueqiang.crm.exceptions.AuthException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * ClassName: PermissionProxy
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/15 - 11:31
 */
@Component
@Aspect
public class PermissionProxy {
    @Resource
    private HttpSession httpSession;

//    切面会拦截指定包下的指定注解
    @Around(value = "@annotation(com.zhangxueqiang.crm.annotation.RequiredPermission)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
//        得到当前用户拥有的权限
        List<String> permissions = (List<String>) httpSession.getAttribute("permissions");
        if(null==permissions||permissions.size()<1){
//            抛出认证异常
            throw new AuthException();
        }

//        得到对应的目标方法
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
//        得到方法上的注解
        RequiredPermission declaredAnnotation = methodSignature.getMethod().getDeclaredAnnotation(RequiredPermission.class);
//        得到状态码
        if(!permissions.contains(declaredAnnotation.code())){
            throw new AuthException();
        }

        result = pjp.proceed();
        return result;
    }

}
