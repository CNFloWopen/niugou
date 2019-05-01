package com.CNFloWopen.niugou.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/shopadmin",method = RequestMethod.GET)
public class ShopAdminController {
    //注册页面
    @RequestMapping("/shopoperation")
    public String shopOperation()
    {
        return "shop/shopoperation";
    }
    //商铺列表
    @RequestMapping(value = "/shoplist")
    public String shopList()
    {
        return "shop/shoplist";
    }
    //管理页面
    @RequestMapping(value = "/shopmanagement")
    public String shopManagement()
    {
        return "shop/shopmanagement";
    }
    //店铺注册
    @RequestMapping(value = "/registershop")
    public String shopEdit()
    {
        return "shop/shopedit";
    }
    //商品类别界面
    @RequestMapping(value = "/productcategorymanagement",method = RequestMethod.GET)
    public String productCategoryManagement()
    {
        return "shop/productcategorymanagement";
    }

    @RequestMapping(value = "/productoperation",method = RequestMethod.GET)
    public String productOperation()
    {
        return "shop/productoperation";
    }

    @RequestMapping(value = "/productmanagement",method = RequestMethod.GET)
    public String productManagement()
    {
        return "shop/productmanagement";
    }

    @RequestMapping(value = "/shopauthmanagement")
    public String shopAuthManagement()
    {
        return "shop/shopauthmanagement";
    }

    @RequestMapping(value = "/shopauthedit")
    public String shopAuthEdit()
    {
//        转发到授权编辑页面
        return "shop/shopauthedit";
    }

    @RequestMapping(value = "/operationsuccess",method = RequestMethod.GET)
    public String operationSuccess()
    {
//        转发到操作成功页面
        return "shop/operationsuccess";
    }

    @RequestMapping(value = "/operationfail",method = RequestMethod.GET)
    public String operationFail()
    {
//        转发到操作失败页面
        return "shop/operationfail";
    }

    @RequestMapping(value = "/productbuycheck",method = RequestMethod.GET)
    public String productBuyCheck()
    {
//        转发到店铺消费记录页面
        return "shop/productbuycheck";
    }
}