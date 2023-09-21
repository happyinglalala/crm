package com.zhangxueqiang.crm.dao;

import com.zhangxueqiang.crm.vo.Details;

public interface DetailsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Details record);

    int insertSelective(Details record);

    Details selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Details record);

    int updateByPrimaryKey(Details record);
}