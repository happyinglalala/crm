package com.zhangxueqiang.crm;

import com.alibaba.fastjson.JSON;
import com.zhangxueqiang.crm.base.ResultInfo;
import com.zhangxueqiang.crm.exceptions.AuthException;
import com.zhangxueqiang.crm.exceptions.NoLoginException;
import com.zhangxueqiang.crm.exceptions.ParamsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ClassName: GlobalExceptionResolver
 * Description:
 *
 * @Author: 张学强
 * @Create: 2023/9/8 - 17:32
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    /**
     *
     * @param httpServletRequest    请求对象
     * @param httpServletResponse   响应对象
     * @param handle 方法对象
     * @param e 异常对象
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handle, Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
//        设置异常信息
        modelAndView.addObject("code",500);
        modelAndView.addObject("msg","系统异常请重试！！！");

        if(e instanceof NoLoginException){
            modelAndView = new ModelAndView("redirect:index");
            return modelAndView;
        }

//        判断HandlerMethod
        if(handle instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handle;
//            获取方法上注解的ResponseBody注解对象
            ResponseBody responseBody = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
//            判断对象是否为空
            if(responseBody == null){
//                返回视图
                if(e instanceof ParamsException){
                    ParamsException pex = (ParamsException) e;
                    modelAndView.addObject("code",pex.getCode());
                    modelAndView.addObject("msg",pex.getMsg());
                } else if(e instanceof AuthException){//认证异常
                    AuthException pex = (AuthException) e;
                    modelAndView.addObject("code",pex.getCode());
                    modelAndView.addObject("msg",pex.getMsg());
                }
                return modelAndView;
            }else {
//                返回对象
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(500);
                resultInfo.setMsg("系统异常请重试！！！");
                if(e instanceof ParamsException){
                    ParamsException pex = (ParamsException) e;
                    resultInfo.setMsg(pex.getMsg());
                    resultInfo.setCode(pex.getCode());
                }else if(e instanceof AuthException){//认证异常
                    AuthException pex = (AuthException) e;
                    resultInfo.setMsg(pex.getMsg());
                    resultInfo.setCode(pex.getCode());
                }
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                PrintWriter out = null;
                try{
                    out = httpServletResponse.getWriter();
                    String json = JSON.toJSONString(resultInfo);
                    out.write(json);
                } catch (IOException ex) {
                    e.printStackTrace();
                } finally {
                    if(out != null){
                        out.close();
                    }
                }

                return null;
            }
        }


        return null;
    }
}
