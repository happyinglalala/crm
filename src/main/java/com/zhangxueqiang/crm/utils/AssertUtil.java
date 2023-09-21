package com.zhangxueqiang.crm.utils;

import com.zhangxueqiang.crm.exceptions.ParamsException;

public class AssertUtil {


    public  static void isTrue(Boolean flag,String msg){
        if(flag){
            throw  new ParamsException(msg);
        }
    }

}
