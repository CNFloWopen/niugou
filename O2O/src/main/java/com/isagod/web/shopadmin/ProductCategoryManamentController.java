package com.isagod.web.shopadmin;

import com.isagod.dto.ProductCategoryExecution;
import com.isagod.dto.Result;
import com.isagod.entity.ProductCategory;
import com.isagod.entity.Shop;
import com.isagod.enums.ProductCategoryStateEnum;
import com.isagod.exceptions.ProductCategoryOperationException;
import com.isagod.service.ProductCategoryService;
import com.isagod.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shopadmin")
public class ProductCategoryManamentController {
    @Autowired
    private ProductCategoryService productCategoryService;

    //获取商铺列表
    @RequestMapping(value = "/getproductcategorylist",method = RequestMethod.GET)
    @ResponseBody
    public Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request)
    {
        //测试用到，因为在登陆验证的时候已经进行了id的植入，所以可以注释掉
//        Shop shop = new Shop();
//        shop.setShopId(1L);
//        request.getSession().setAttribute("currentShop",shop);
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;
        if (currentShop != null && currentShop.getShopId() >0)
        {
            list =  productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true,list);

        }else {
            ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false,ps.getState(),ps.getStateInfo());
        }
    }

    //添加商铺
    @RequestMapping(value = "/addproductcategorys",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
                                                  HttpServletRequest request)
    {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //循环遍历shopid，把shopid放入进去
        for (ProductCategory pc : productCategoryList) {
            pc.setShopId(currentShop.getShopId());
        }
        //判断productCategoryList是否为空
        if (productCategoryList !=null && productCategoryList.size() >0)
        {
            try {
                ProductCategoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState())
                {
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",pe.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }

        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请至少输入一个商品类别的店铺");
        }
        return modelMap;
    }

    //    删除商品
    @RequestMapping(value = "/removeproductcategory",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> removeProductCategory(Long productCategoryId, HttpServletRequest request)
    {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        if (productCategoryId != null && productCategoryId >0)
        {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                ProductCategoryExecution pe = productCategoryService.deleteProdcutCategory(productCategoryId,currentShop.getShopId());
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState())
                {
                    modelMap.put("success",true);
                }else
                {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",pe.getStateInfo());
                }

            }catch (RuntimeException e)
            {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
                return modelMap;
            }
        }else
        {
            modelMap.put("success",false);
            modelMap.put("errMsg","请至少输入一个商品类别的店铺");
        }
        return modelMap;
    }
}
