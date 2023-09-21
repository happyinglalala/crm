package com.zhangxueqiang.crm.service;

import com.zhangxueqiang.crm.base.BaseService;
import com.zhangxueqiang.crm.dao.ModuleMapper;
import com.zhangxueqiang.crm.dao.PermissionMapper;
import com.zhangxueqiang.crm.dao.RoleMapper;
import com.zhangxueqiang.crm.utils.AssertUtil;
import com.zhangxueqiang.crm.vo.Permission;
import com.zhangxueqiang.crm.vo.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName: RoleService
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/13 - 16:07
 */
@Service
public class RoleService extends BaseService<Role,Integer> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private ModuleMapper moduleMapper;

//    查询所有的角色列表
    public List<Map<String,Object>> queryAllRoles(Integer userId){
        return roleMapper.queryAllRoles(userId);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void addRole(Role role) {
//        判断角色名不能为空
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名称不能为空！！！");
//        查找是否有相同角色名的角色
        Role temp = roleMapper.queryByRoleName(role.getRoleName());
//        如果角色名不相同
        if(temp != null){
//        如果角色名相同
//            判断角色是否被禁用
            AssertUtil.isTrue(temp.getIsValid()==1,"角色名已重复！！！");
//            设置角色默认值
            temp.setRoleRemark(role.getRoleRemark());
            temp.setIsValid(1);
            temp.setUpdateDate(new Date());
            AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(temp)<1,"添加角色失败233！！！");
        }else {
            role.setIsValid(1);
            role.setCreateDate(new Date());
            role.setUpdateDate(new Date());
            AssertUtil.isTrue(roleMapper.insertSelective(role)<1,"添加角色失败！！！");
        }
    }

    public void updateRole(Role role) {
//        参数验证
//          role主键非空
        AssertUtil.isTrue(role.getId()==null,"角色不存在！！！");
        AssertUtil.isTrue(roleMapper.selectByPrimaryKey(role.getId())==null,"角色不存在！！！");
//          role角色名不能为空
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名称不能为空！！！");
//          role角色名不能重复
//        查找是否有相同角色名的角色
        Role temp = roleMapper.queryByRoleName(role.getRoleName());
        if(temp != null){
            AssertUtil.isTrue(temp.getIsValid()==1&&(!temp.getId().equals(role.getId())),"角色名重复！！！");
            if(temp.getIsValid()==0){
                AssertUtil.isTrue(roleMapper.deleteByPrimaryKey(temp.getId())!=1,"系统异常！！！！kljlk");
            }
            role.setIsValid(1);
            role.setUpdateDate(new Date());
            AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role)!=1,"更新失败！！！");
        }else {
            role.setIsValid(1);
            role.setUpdateDate(new Date());
            AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role)!=1,"更新失败！！！");
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer roleId) {
        AssertUtil.isTrue(roleId==null,"角色不存在！！！");
        Role role = roleMapper.selectByPrimaryKey(roleId);
        AssertUtil.isTrue(role==null,"角色不存在！！！");
        role.setIsValid(0);
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role)!=1,"删除失败！！！");
    }

    /**
     * 角色绑定权限
     *      1.参数校验
     *          roleId  非空，有值
     *      2.执行添加方法
     *          删除以前的权限，增加现在绑定的权限
     * @param mIds
     * @param roleId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addGrant(Integer[] mIds, Integer roleId) {
//        1.参数校验
        AssertUtil.isTrue(roleId==null,"角色获取异常!!!");
        AssertUtil.isTrue(roleMapper.selectByPrimaryKey(roleId)==null,"角色不存在！！！");
//        2.执行添加方法
//        查询需要删除的个数
        Integer i = permissionMapper.selectModuleByRoleId(roleId);
//        删除权限并与查询的删除数进行比较
        if(i>0){
            AssertUtil.isTrue(permissionMapper.deleteByRoleId(roleId)!=i,"更新失败！！！！！yyy");
        }
        if(mIds!=null&&mIds.length>0){
            //        添加权限
            List<Permission> list = new ArrayList<>();
            for(int x = 0;x < mIds.length;x++){
                Permission permission = new Permission();
                permission.setRoleId(roleId);
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                permission.setModuleId(mIds[x]);
                permission.setAclValue(moduleMapper.selectByPrimaryKey(mIds[x]).getOptValue());
                list.add(permission);
            }
            AssertUtil.isTrue(permissionMapper.insertBatch(list)!=mIds.length,"角色权限添加失败!!!");
        }



    }
}
