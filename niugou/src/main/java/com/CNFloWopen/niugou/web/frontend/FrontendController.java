package com.CNFloWopen.niugou.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/frontend")
public class FrontendController {
    /**
     * 主页面路由
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index()
    {
        return "frontend/index";
    }

    /**
     * 进入店铺查询主页面路由
     * @return
     */
    @RequestMapping(value = "/shoplist",method = RequestMethod.GET)
    public String showShopList()
    {
        return "frontend/shoplist";
    }

    /**
     * 店铺详情页路由
     * @return
     */
    @RequestMapping(value = "/shopdetail",method = RequestMethod.GET)
    public String showShopDetail()
    {
        return "frontend/shopdetail";
    }

    /**
     * 商品详情页路由
     * @return
     */
    @RequestMapping(value = "/productdetail",method = RequestMethod.GET)
    public String showProductDetail()
    {
        return "frontend/productdetail";
    }

    @RequestMapping(value = "/cart",method = RequestMethod.GET)
    public String showcart()
    {
        return "frontend/cart";
    }
}
