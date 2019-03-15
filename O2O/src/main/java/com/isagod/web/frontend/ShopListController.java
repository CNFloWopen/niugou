package com.isagod.web.frontend;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.isagod.dao.ShopDao;
import com.isagod.dto.ShopExecution;
import com.isagod.entity.Area;
import com.isagod.entity.Shop;
import com.isagod.entity.ShopCategory;
import com.isagod.service.AreaService;
import com.isagod.service.ShopCategoryService;
import com.isagod.service.ShopService;
import com.isagod.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.Oneway;
import javax.print.attribute.standard.MediaName;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/frontend")
public class ShopListController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;

    /**
     * 返回商品列表页中的ShopCategory列表（二级或者一级），以及区域信息列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopspageinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> listShopsPageInfo(HttpServletRequest request)
    {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //试着从前端请求中获取parentId
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        if (parentId != -1) {
            //如果parentId存在，则取出一级shopCategory下的二级shopCategory列表
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            try {
                //如果parentId不存在，则取出所有的一级shopCategory（用户在首页选择的是全部商店列表）
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        }
        modelMap.put("shopCategoryList", shopCategoryList);
        List<Area> areaList = null;
        try {
            //获取区域列表信息
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }

    /**
     * 获取指定查询下的商铺列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshops")
    @ResponseBody
    public Map<String,Object> listShops(HttpServletRequest request)
    {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        //获取一页需要显示的数据条数
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        //进行非空判断
        if (pageIndex>-1 && pageSize>-1)
        {
            //获取一级列表的ParentId
            long parentId = HttpServletRequestUtil.getLong(request,"parentId");
            //获取二级列表的Id
            long shopCategoryId = HttpServletRequestUtil.getLong(request,"shopCategoryId");
            //获取区域列表的ID
            int areaId = HttpServletRequestUtil.getInt(request,"areaId");
            //获取模糊查询的名字
            String shopName = HttpServletRequestUtil.getString(request,"shopName");
            //获取组合之后的查询条件
            Shop shopCondition = compactShopCondition4Search(parentId,shopCategoryId,areaId,shopName);
            //根据查询条件和分页信息获取店铺列表，并返回总数
            ShopExecution se = shopService.getShopList(shopCondition,pageIndex,pageSize);
            modelMap.put("shopList",se.getShopList());
            modelMap.put("count",se.getCount());
            modelMap.put("success",true);

        }else {
            modelMap.put("success",false);
            modelMap.put("errMag","empty pageIndex or pageSize");
        }
        return modelMap;
    }

    /**
     * 组合查询条件，并将条件封装到ShopCondition对象里面返回
     * @param parentId
     * @param shopCategoryId
     * @param areaId
     * @param shopName
     * @return
     */
    private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition = new Shop();
        if (parentId != -1L)
        {
            //查询某个一级ShopCategory下面的所有ShopCategory里面的商铺列表
            ShopCategory childCategory = new ShopCategory();
            ShopCategory parentCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            childCategory.setParent(parentCategory);
            shopCondition.setShopCategory(childCategory);
        }
        if (shopCategoryId != -1L)
        {
            //查询某个二级ShopCategory下面的店铺列表
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (areaId != -1L)
        {
            //查询某个区域Id下的店铺列表
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        if (shopName != null)
        {
            //查询名字里面包含shopName
            shopCondition.setShopName(shopName);
        }
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }
}
