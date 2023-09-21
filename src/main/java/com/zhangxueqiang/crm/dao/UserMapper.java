package com.zhangxueqiang.crm.dao;

import com.zhangxueqiang.crm.base.BaseMapper;
import com.zhangxueqiang.crm.vo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User, Integer> {
    User queryUserByName(String userName);

    List<Map<String,Object>> queryAllSales();

}
