package com.zhangxueqiang.crm.service;

import com.zhangxueqiang.crm.base.BaseService;
import com.zhangxueqiang.crm.dao.UserMapper;
import com.zhangxueqiang.crm.dao.UserRoleMapper;
import com.zhangxueqiang.crm.model.UserModel;
import com.zhangxueqiang.crm.utils.AssertUtil;
import com.zhangxueqiang.crm.utils.Md5Util;
import com.zhangxueqiang.crm.utils.PhoneUtil;
import com.zhangxueqiang.crm.utils.UserIDBase64;
import com.zhangxueqiang.crm.vo.User;
import com.zhangxueqiang.crm.vo.UserRole;
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
 * ClassName: UserService
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/7 - 21:27
 */
@Service
public class UserService extends BaseService<User,Integer> {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;
//    Service层 （业务逻辑层：非空判断、条件判断等业务逻辑处理）
//            1. 参数判断，判断用户姓名、用户密码非空弄
//    如果参数为空，抛出异常（异常被控制层捕获并处理）
//            2. 调用数据访问层，通过用户名查询用户记录，返回用户对象
//            3. 判断用户对象是否为空
//    如果对象为空，抛出异常（异常被控制层捕获并处理）
//            4. 判断密码是否正确，比较客户端传递的用户密码与数据库中查询的用户对象中的用户密码
//    如果密码不相等，抛出异常（异常被控制层捕获并处理）
//            5. 如果密码正确，登录成功
    public UserModel userLogin(String userName,String userPwd){
//        用户名与用户密码的非空校验
        checkLoginParams(userName,userPwd);

//        用用户姓名查询用户
        User user = userMapper.queryUserByName(userName);
        AssertUtil.isTrue(null==user,"用户名不存在！！！");

//        查询到的用户与密码进行比较
        checkUserPwd(userPwd,user.getUserPwd(),"密码不正确！！！");

//        构建返回给客户端的用户对象
        UserModel userModel = buildUserInfo(user);

//        返回构建的用户对象
        return userModel;
    }

//    Service层
//            1. 接收四个参数 （用户ID、原始密码、新密码、确认密码）
//            2. 通过用户ID查询用户记录，返回用户对象
//            3. 参数校验
//    待更新用户记录是否存在 （用户对象是否为空）
//    判断原始密码是否为空
//    判断原始密码是否正确（查询的用户对象中的用户密码是否原始密码一致）
//    判断新密码是否为空
//    判断新密码是否与原始密码一致 （不允许新密码与原始密码）
//    判断确认密码是否为空
//            判断确认密码是否与新密码一致
//            4. 设置用户的新密码
//    需要将新密码通过指定算法进行加密（md5加密）
//            5. 执行更新操作，判断受影响的行数


//            1. 接收四个参数 （用户ID、原始密码、新密码、确认密码）
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePassword(Integer userId,String oldPassword,String newPassword,String repeatPwd){
//            2. 通过用户ID查询用户记录，返回用户对象
        User user = userMapper.selectByPrimaryKey(userId);
        //            3. 参数校验
//    待更新用户记录是否存在 （用户对象是否为空）
        AssertUtil.isTrue(user==null,"待更新的记录不存在！！！");
        checkPasswordParams(user,oldPassword,newPassword,repeatPwd);
        user.setUserPwd(Md5Util.encode(newPassword));
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)<1,"修改密码失败！！！");
    }

    private void checkPasswordParams(User user,String oldPassword, String newPassword, String repeatPwd) {
        //    判断原始密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"原始密码不能为空！！！");
//    判断原始密码是否正确（查询的用户对象中的用户密码是否原始密码一致）
        checkUserPwd(oldPassword,user.getUserPwd(),"原始密码不正确");
//    判断新密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"新密码是否为空！！！");
//    判断新密码是否与原始密码一致 （不允许新密码与原始密码）
        AssertUtil.isTrue(oldPassword.equals(newPassword),"新密码不能与原始密码一致");

//    判断确认密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(repeatPwd),"确认密码不能为空！！！");
//            判断确认密码是否与新密码一致
        AssertUtil.isTrue( !newPassword.equals(repeatPwd),"确认密码与新密码不符！！！");
    }


    /**
     * 构建返回给客户端的用户对象
     * @param user
     */
    private UserModel buildUserInfo(User user) {
        UserModel userModel = new UserModel();
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
//        userModel.setId(user.getId());
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;
    }

    /**
     * 密码判断：
     *      将客户端的密码进行加密
     * @param userPwd
     * @param pwd
     */
    private void checkUserPwd(String userPwd, String pwd,String msg) {
//        传入密码加密
        userPwd = Md5Util.encode(userPwd);
//        判断密码是否相等
        AssertUtil.isTrue(!pwd.equals(userPwd),msg);
    }

    private void checkLoginParams(String userName, String userPwd) {
//        验证用户姓名不能为空
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户姓名不能为空！！！");
//        验证用户密码不能为空
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"用户密码不能为空！！！");


    }

    /**
     * 查询所有的销售人员
     * @return
     */
    public List<Map<String,Object>> queryAllSales(){
//        System.out.println(userMapper.queryAllSales());
        return userMapper.queryAllSales();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(User user) {
//        参数校验
        checkUserParams(user);
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));
        AssertUtil.isTrue(userMapper.insertSelective(user)<1,"用户添加失败！！！");
//        用户角色关联
        relationUserRole(user.getId(),user.getRoleIds());
    }

//    用户角色关联
    private void relationUserRole(Integer userId, String roleIds) {

        Integer count = userRoleMapper.countUserRoleByUserId(userId);
        if(count > 0){
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId)!=count,"用户角色分配失败！！！");
        }
        if(StringUtils.isNotBlank(roleIds)){
//            将用户数据设置到集合中
            List<UserRole> userRoleList = new ArrayList<>();
//            字符串转成数组
            String[] split = roleIds.split(",");
//            遍历数组将每一个得到的用户对象，设置到集合中
            for(String roleId:split){
                UserRole userRole = new UserRole();
                userRole.setRoleId(Integer.valueOf(roleId));
                userRole.setUserId(userId);
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                userRoleList.add(userRole);
            }
            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoleList)!=userRoleList.size(),"用户角色分配失败！！！");

        }





    }

    //参数校验
    private void checkUserParams(User user) {
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()),"用户姓名不能为空！！！");
        User temp = userMapper.queryUserByName(user.getUserName());
        AssertUtil.isTrue(null!=temp&&!user.getId().equals(temp.getId()),"用户名重复！！！");
        AssertUtil.isTrue(user.getEmail()==null,"邮箱号码不能为空！！！");
        AssertUtil.isTrue(user.getPhone()==null,"手机号码不能为空！！！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(user.getPhone()),"手机号码格式不正确！！！");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user){
        AssertUtil.isTrue(null==user.getId(),"待更新记录不存在！！！");
        AssertUtil.isTrue(null==userMapper.selectByPrimaryKey(user.getId()),"待更新记录不存在！！！");
        checkUserParams(user);
        user.setUpdateDate(new Date());
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)!=1,"更新失败！！");
//        用户角色关联
        relationUserRole(user.getId(),user.getRoleIds());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(Integer[] ids) {
        AssertUtil.isTrue(ids==null||ids.length==0,"未选中删除项！！！");

        AssertUtil.isTrue(userMapper.deleteBatch(ids)!=ids.length,"");

//       遍历ids数组
        for(Integer id:ids){
            Integer i = userRoleMapper.countUserRoleByUserId(id);
//            判断角色记录是否存在
            if(i > 0){
//                根据角色Id删除用户记录数
                AssertUtil.isTrue(i!=userRoleMapper.deleteUserRoleByUserId(id),"删除用户角色时出错！！！");
            }
        }
    }
}
