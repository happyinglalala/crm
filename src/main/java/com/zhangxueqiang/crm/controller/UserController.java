package com.zhangxueqiang.crm.controller;

import com.zhangxueqiang.crm.base.BaseController;
import com.zhangxueqiang.crm.base.ResultInfo;
import com.zhangxueqiang.crm.exceptions.ParamsException;
import com.zhangxueqiang.crm.model.UserModel;
import com.zhangxueqiang.crm.query.UserQuery;
import com.zhangxueqiang.crm.service.UserService;
import com.zhangxueqiang.crm.utils.LoginUserUtil;
import com.zhangxueqiang.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ClassName: UserController
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/7 - 21:29
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * 登录功能
     * @param userName
     * @param userPwd
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public ResultInfo userLogin(String userName,String userPwd){
        ResultInfo resultInfo = new ResultInfo();
        UserModel user = userService.userLogin(userName, userPwd);
        resultInfo.setResult(user);
       /* try{
            UserModel user = userService.userLogin(userName, userPwd);
            resultInfo.setResult(user);

        }catch (ParamsException p){
            resultInfo.setCode(p.getCode());
            resultInfo.setMsg(p.getMsg());
            p.printStackTrace();
        }catch (Exception e){
            resultInfo.setMsg("登录失败！！！");
            resultInfo.setCode(500);
            e.printStackTrace();
        }*/
        return resultInfo;
    }

    @RequestMapping("updatePwd")
    @ResponseBody
    public ResultInfo updateUserPassword(HttpServletRequest request,
           String oldPassword,String newPassword,String repeatPassword){
        ResultInfo resultInfo = new ResultInfo();
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        userService.updatePassword(userId,oldPassword,newPassword,repeatPassword);
        /*try{
            int userId = LoginUserUtil.releaseUserIdFromCookie(request);
            userService.updatePassword(userId,oldPassword,newPassword,repeatPassword);
        }catch (ParamsException p){
            resultInfo.setCode(p.getCode());
            resultInfo.setMsg(p.getMsg());
            p.printStackTrace();
        }catch (Exception e){
            resultInfo.setCode(500);
            resultInfo.setMsg("更新密码失败！！！");
            e.printStackTrace();
        }*/
        return resultInfo;
    }

    @RequestMapping("toPasswordPage")
    public String toPasswordPage(){
        return "user/password";
    }

    @RequestMapping("queryAllSales")
    @ResponseBody
    public List<Map<String,Object>> queryAllSales(){
        return userService.queryAllSales();
    }

//    查询用户列表
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> selectByParams(UserQuery userQuery){
        return userService.queryByParamsForTable(userQuery);
    }

    @RequestMapping("index")
    public String index(){
        return "user/user";
    }

    @PostMapping("addUser")
    @ResponseBody
    public ResultInfo addUser(User user){
        userService.addUser(user);
        return success("添加成功！！！");
    }

    @RequestMapping("toAddOrUpdateUserPage")
    public String toAddOrUpdateUserPage(Integer id,HttpServletRequest httpServletRequest){
        if(id != null){
            User user = userService.selectByPrimaryKey(id);
            httpServletRequest.setAttribute("user",user);
        }
        return "user/add_update";
    }

    @RequestMapping("updateUser")
    @ResponseBody
    public ResultInfo updateUser(User user){
        userService.updateUser(user);
        return success("更新成功！！！");
    }

    @PutMapping("deleteUser")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids){
        userService.deleteByIds(ids);
        return success("删除成功");
    }

}
