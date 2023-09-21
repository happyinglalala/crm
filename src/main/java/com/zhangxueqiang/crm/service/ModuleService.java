package com.zhangxueqiang.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangxueqiang.crm.base.BaseService;
import com.zhangxueqiang.crm.dao.ModuleMapper;
import com.zhangxueqiang.crm.dao.PermissionMapper;
import com.zhangxueqiang.crm.model.TreeModel;
import com.zhangxueqiang.crm.utils.AssertUtil;
import com.zhangxueqiang.crm.vo.Module;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.*;

/**
 * ClassName: ModuleService
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/14 - 17:20
 */
@Service
public class ModuleService extends BaseService<Module,Integer> {
    @Resource
    private ModuleMapper moduleMapper;
    @Resource
    private PermissionMapper permissionMapper;

//    查询所有的资源列表
    public List<TreeModel> queryAllModules(Integer roleId){
        List<TreeModel> treeModels = moduleMapper.queryAllModels(roleId);
        /*List<Integer> permissionIds = permissionMapper.queryRoleHasModelIdByRoleId(roleId);
        if(permissionIds!=null && permissionIds.size()>0){
            for (TreeModel treeModel:treeModels){
                if(permissionIds.contains(treeModel.getId())){
                    treeModel.setChecked(true);
                }
            }
        }*/
        return treeModels;
    }


//    查询资源数据
    public Map<String,Object> queryModuleList(){
        Map<String,Object> map = new HashMap<>();
        List<Module> modules = moduleMapper.queryModuleList();
        map.put("code",0);
        map.put("msg","");
        map.put("count",modules.size());
        map.put("data",modules);
        return map;
    }


    /**
     *  参数校验
     * 设置默认值
     * 执行添加操作
     * @param module
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addMudule(Module module){
//        参数校验
        checkCanShu(module);
//        设置默认值
        module.setIsValid((byte) 1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());
//        执行添加操作
        moduleMapper.insertSelective(module);
    }

    private void checkCanShu(Module module) {
        //       层级grade 非空 0、1、2
        Integer grade = module.getGrade();
        AssertUtil.isTrue(grade==null||(grade!=1&&grade!=2&&grade!=0),"层级输入错误！！！");
//        模块名非空，同一层级下，模块名不重复
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"模块名不能为空！！！");
        AssertUtil.isTrue(moduleMapper.selectMuduleByMuduleNameInGrade(module.getModuleName(),module.getGrade())!=0,"模块名不能重复");
//        二级菜单url非空不可重复
        if(grade==2){
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"二级菜单地址不能为空！！！");
            AssertUtil.isTrue(moduleMapper.selectModuleByErUrl(module.getUrl())!=0,"二级菜单地址不能相同！！！");
        }
//        1级别以下的菜单必须存在
        if(grade==0){
            module.setParentId(-1);
        }else {
            AssertUtil.isTrue(module.getParentId()==null,"第"+grade+"级别的父菜单不能为空！！！");
            AssertUtil.isTrue(moduleMapper.selectModuleByParentId(module.getParentId())==0,"父级菜单不存在！！！");
        }
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"权限码不能为空！！！");
        AssertUtil.isTrue(moduleMapper.selectModuleByOptValue(module.getOptValue())!=0,"权限码不能重复！！！");

    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Module module){
        AssertUtil.isTrue(module.getId()==null,"权限不存在！！！");
        Module temp = moduleMapper.selectByPrimaryKey(module.getId());
        System.out.println(temp);
        AssertUtil.isTrue(temp==null,"权限不存在！！！");
        //       层级grade 非空 0、1、2
        Integer grade = module.getGrade();
        AssertUtil.isTrue(grade==null||(grade!=1&&grade!=2&&grade!=0),"层级输入错误！！！");
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"模块名不能为空！！！");
        AssertUtil.isTrue(moduleMapper.selectMuduleByMuduleNameInGrade(module.getModuleName(), module.getGrade())!=0
                &&!temp.getModuleName().equals(module.getModuleName()),"模块名不能重复");
        //        二级菜单url非空不可重复
        if(grade==1){
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"二级菜单地址不能为空！！！");
            AssertUtil.isTrue(moduleMapper.selectModuleByErUrl(module.getUrl())!=0&&!temp.getUrl().equals(module.getUrl()),
                    "二级菜单地址不能相同！！！");
        }
        //        1级别以下的菜单必须存在
        if(grade==0){
            module.setParentId(-1);
        }else {
            AssertUtil.isTrue(module.getParentId()==null,"第"+grade+"级别的父菜单不能为空！！！");
            AssertUtil.isTrue(moduleMapper.selectModuleByParentId(module.getParentId())==0,"父级菜单不存在！！！");
        }
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"权限码不能为空！！！");
        AssertUtil.isTrue(moduleMapper.selectModuleByOptValue(module.getOptValue())!=0&&!temp.getOptValue().equals(module.getOptValue()),
                "权限码不能重复！！！");
        //        设置默认值
        module.setIsValid((byte) 1);
        module.setUpdateDate(new Date());
//        执行更新操作
        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(module)<1,"更新失败！！！");

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteModuleById(Integer roleId) {
        AssertUtil.isTrue(roleId==null,"选择记录不存在！！！");
        Module module = moduleMapper.selectByPrimaryKey(roleId);
        AssertUtil.isTrue(module==null,"选择记录不存在！！！");
        Integer modules1 = moduleMapper.selectModuleBeanByParentId(roleId);
        AssertUtil.isTrue(modules1>0,"子节点还存在！！！");
        Integer count = permissionMapper.countPermissionByModuleId(roleId);
        if(count > 0){
            AssertUtil.isTrue(!permissionMapper.deletePermissionByModuleId(roleId).equals(count) ,"删除失败！！！");
        }
        module.setIsValid((byte) 0);
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(module)<1,"删除失败！！！");
    }
}
