package com.zhangxueqiang.crm.dao;

import com.zhangxueqiang.crm.base.BaseMapper;
import com.zhangxueqiang.crm.model.TreeModel;
import com.zhangxueqiang.crm.vo.Module;

import java.util.List;

public interface ModuleMapper extends BaseMapper<Module,Integer> {

//    查询所有资源列表
    List<TreeModel> queryAllModels(Integer roleId);

//    查询所有的资源数据
    List<Module> queryModuleList();

    Integer selectMuduleByMuduleNameInGrade(String moduleName, Integer grade);

    Integer selectModuleByErUrl(String url);

    Integer selectModuleByOptValue(String optValue);

    Integer selectModuleByParentId(Integer parentId);

    Integer selectModuleBeanByParentId(Integer id);
}