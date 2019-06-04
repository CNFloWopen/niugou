package com.CNFloWopen.niugou.web.superadmin;

import com.CNFloWopen.niugou.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/superadmin")
public class CacheController {
    public String AREALISTKEY = "arealist";
    public String HLLISTKEY = "headlinelist";
    public String SCLISTKEY = "shopcategorylist";
    @Autowired
    CacheService cacheService;
    //注册页面
    @RequestMapping("/removeArea")
    public String removeArea()
    {
        cacheService.removeFromCache(AREALISTKEY);
        return "shop/removesuccess";
    }
    //商铺列表
    @RequestMapping(value = "/removeShopCategory")
    public String removeShopCategory()
    {
        cacheService.removeFromCache(SCLISTKEY);
        return "shop/removesuccess";
    }
    //管理页面
    @RequestMapping(value = "/removeHeadLine")
    public String removeHeadLine()
    {
        cacheService.removeFromCache(HLLISTKEY);
        return "shop/removesuccess";
    }
}
