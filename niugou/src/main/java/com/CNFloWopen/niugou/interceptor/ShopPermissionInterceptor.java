package com.CNFloWopen.niugou.interceptor;


import com.CNFloWopen.niugou.entity.Shop;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 店家管理系统操作验证拦截器
 */
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {
    /**
     * 主要做事前进行拦截，用户发生操作前改写preHandle里面的逻辑，进行拦截
     * handler要拦截的Interceptor
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        从session中获取当前的店铺
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        @SuppressWarnings("unchecked")
//      从Session中获取当前用户可操作的店铺列表
        List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
//      非空判断
        if (currentShop!=null && shopList!=null)
        {
//            遍历可操作的店铺
            for (Shop shop:
                 shopList) {
//            如果当前店铺在可操作的列表里返回了true，进行接下来的用户操作
                if (shop.getShopId() == currentShop.getShopId())
                {
                    return true;
                }
            }
        }
//        不再可操作列表中，就拦截
        return false;
    }
}
