package com.CNFloWopen.niugou.interceptor;


import com.CNFloWopen.niugou.entity.PersonInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 店家管理系统登录的拦截器
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * 主要做事前进行拦截，用户发生操作前改写preHandle里面的逻辑，进行拦截
     * handler要拦截的Interceptor
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        从session中取出用户信息来
        Object userobj = request.getSession().getAttribute("user");
        if (userobj != null) {
//            若用户信息不为空，则将session里面的用户信息转化为personInfo实体类象
            PersonInfo user = (PersonInfo) userobj;
//            做空值判断，确保userId不为空并且该账号可用状态为1，并且用户类型为2(店家)
            if (user != null && user.getUserId() != null && user.getUserId() > 0 && user.getEnableStatus() == 1) {
//                同过验证则返回true，拦截器返回true后，用户接下来的操作恢复正常
                return true;
            }
        }
//            若不满足登录验证，则回到登录界面
        PrintWriter out = response.getWriter();
        out.write("<html>");
        out.write("<script>");
        out.write("window.open('" + request.getContextPath() + "/local/login?usertype=2','_self')");
        out.write("</script>");
        out.write("</html>");
        return false;
    }
}
