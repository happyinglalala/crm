package com.zhangxueqiang.crm.annotation;

import java.lang.annotation.*;

/**
 * ClassName: RequiredPermission
 * Description:定义方法需要资源的权限码
 *
 * @Author: 张学强
 * @Create: 2023/9/15 - 11:22
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiredPermission {
    String code() default "";
}
