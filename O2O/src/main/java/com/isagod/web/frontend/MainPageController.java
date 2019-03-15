package com.isagod.web.frontend;

import com.isagod.entity.HeadLine;
import com.isagod.entity.ShopCategory;
import com.isagod.service.HeadLineService;
import com.isagod.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/frontend")
public class MainPageController {
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private HeadLineService headLineService;
    @RequestMapping(value = "/listmainpageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listMainPageInfo()
    {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList  = new ArrayList<ShopCategory>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(null);
            modelMap.put("shopCategoryList",shopCategoryList);
        }catch (Exception e)
        {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        List<HeadLine> headLineList = new ArrayList<HeadLine>();
        try {
            HeadLine headLineCondition = new HeadLine();
            headLineCondition.setEnableStatus(1);
            headLineList = headLineService.getHeadLineList(headLineCondition);
            modelMap.put("headLineList",headLineList);
        }catch (Exception e)
        {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        modelMap.put("success",true);
        return modelMap;
    }
}
