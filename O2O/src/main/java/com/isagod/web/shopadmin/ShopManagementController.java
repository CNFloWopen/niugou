package com.isagod.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isagod.dto.ImageHolder;
import com.isagod.dto.ShopExecution;
import com.isagod.entity.Area;
import com.isagod.entity.PersonInfo;
import com.isagod.entity.Shop;
import com.isagod.entity.ShopCategory;
import com.isagod.enums.ShopStateEnum;
import com.isagod.exceptions.ShopOperationException;
import com.isagod.service.AreaService;
import com.isagod.service.ShopCategoryService;
import com.isagod.service.ShopService;
import com.isagod.util.CodeUtil;
import com.isagod.util.HttpServletRequestUtil;
import com.isagod.util.ImageUtil;
import com.isagod.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商铺管理
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;


    /**
     * 根据id获取店铺信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopById(HttpServletRequest request)
    {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        Long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if (shopId > -1)
        {
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop",shop);
                modelMap.put("areaList",areaList);
                modelMap.put("success",true);
            }catch (Exception e)
            {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty shopId");
        }
        return modelMap;
    }

    /**
     * 更新修改店铺
     * @param request
     * @return
     */

    @RequestMapping(value = "/modifyshop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyShop(HttpServletRequest request)
    {
        Map<String,Object> modelMap = new HashMap<String, Object>();
//        判断验证码是否相同
        if (!CodeUtil.checkVerifyCode(request))
        {
            modelMap.put("success",false);
            modelMap.put("errMsg"," 您输入的验证码有误");
            return modelMap;
        }
//        1.接收并转化相应的参数，包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            //让json数据与实体类的数据相对应
            shop = mapper.readValue(shopStr,Shop.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
//        文件上传解析器，解析request中的信息，就是在上下文中找===上传文件的信息
        CommonsMultipartResolver commonsMultipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断request用户给的请求注册中式不是上传了文件
        if (commonsMultipartResolver.isMultipart(request))
        {
//            强制转化
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
//            提取出上传的文件并命名为shopImg
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
//        2.修改店铺信息
        if (shop != null && shop.getShopId() != null) {
            ShopExecution se;
            try {
                if (shopImg==null){
                    //没有图片的话
                    se = shopService.modifyShop(shop, null);
                }else {
                    ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
                    se = shopService.modifyShop(shop,imageHolder);
                }

                if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (ShopOperationException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg",e.getMessage());
            }

//            判断是不是在审核中，此刻的state应该为0

            return modelMap;
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺Id");
            return modelMap;
        }
    }

    /**
     * 在前端显示店铺的注册信息
     * @return
     */
    @RequestMapping(value = "/getshopinitinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopInitInfo()
    {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    /**
     * 返回商铺列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopList(HttpServletRequest request)
    {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        user.setName("test");
        request.getSession().setAttribute("user",user);
        user = (PersonInfo)request.getSession().getAttribute("user");
        try{
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            //由于个人创建的店铺是比较少的所以设为0
            ShopExecution se = shopService.getShopList(shopCondition,0,100);
            modelMap.put("shopList",se.getShopList());
            modelMap.put("user",user);
            modelMap.put("success",true);
        }catch (Exception e)
        {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    /**
     * 店铺管理相关页面的方法
     * 就是查看有没有登陆，不准予不登陆的上来页面
     * 而登陆过的，能利用浏览器的Session直接进入，不需要重新登陆
     * @param request
     * @return
     */
    @RequestMapping(value = "getshopmanagementinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopManagementInfo(HttpServletRequest request)
    {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        //如果没有在前端发现shopId
        if (shopId <= 0)
        {
            /**
             * 那从session中取获取shopId，
             * 理解的意思就是，看你有没有登陆过。
             * 登陆过的话，就不要再次登陆了，从session中获取
             */

            Object currentShopObj = request.getSession().getAttribute("currentShop");
            //如果你从来就没登陆过，在session也为空
            if (currentShopObj == null)
            {
                //让你重定向，跳转网页，不让你登进去
                modelMap.put("redirect",true);
                modelMap.put("url","/o2o/shopadmin/shoplist");
            }else {
                //如果登陆过，就直接进来
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect",false);
                modelMap.put("shopId",currentShop.getShopId());
            }
        }else {
            //从在前台登陆，那就shopId不会为空了。获取到你的shopId，
            // 把你的shopId放入到currentShop中，再把currentShop放入Session
            // 和上面的--=currentShopObj=--取到的Session--=currentShop=--对应起来
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            modelMap.put("redirect",false);
        }
        return modelMap;
    }

    /**
     * 店铺注册
     * @param request
     * @return
     */
    @RequestMapping(value = "/registershop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> registerShop(HttpServletRequest request)
    {
        Map<String,Object> modelMap = new HashMap<String, Object>();
//        判断验证码是否相同
        if (!CodeUtil.checkVerifyCode(request))
        {
            modelMap.put("success",false);
            modelMap.put("errMsg"," 您输入的验证码有误");
            return modelMap;
        }
//        1.接收并转化相应的参数，包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            //让json数据与实体类的数据相对应
            shop = mapper.readValue(shopStr,Shop.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
//        文件上传解析器，解析request中的信息，就是在上下文中找===上传文件的信息
        CommonsMultipartResolver commonsMultipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断request用户给的请求注册中式不是上传了文件
        if (commonsMultipartResolver.isMultipart(request))
        {
//            强制转化
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
//            提取出上传的文件并命名为shopImg
            // shopImg是和前端约定好的变量名
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }else
        {
            modelMap.put("success",false);
            modelMap.put("errMsg","上传的图片不能为空");
            return modelMap;
        }
//        2.注册店铺
        if (shop != null && shopImg != null) {
            //用店主的信息来获取店铺的信息
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);
//            File shopImgFile = new File(PathUtil.getImgBasePath()+ImageUtil.getRandomFileName());
//            try {
//                //没有的话就创建文件
//                shopImgFile.createNewFile();
//            } catch (IOException e) {
//                modelMap.put("success",false);
//                modelMap.put("errMsg",e.getMessage());
//                return modelMap;
//            }
//            try {
//                //转化
//                inputStreamToFile(shopImg.getInputStream(),shopImgFile);
//            } catch (IOException e) {
//                modelMap.put("success",false);
//                modelMap.put("errMsg",e.getMessage());
//                return modelMap;
//            }
            ShopExecution se;
            try {
                ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
                se = shopService.addShop(shop, imageHolder);
                if (se.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put("success", true);
                    //一个用户可以创建多个店铺
                    List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                    //第一次创建店铺
                    if (shopList == null || shopList.size() == 0) {
                        shopList = new ArrayList<Shop>();
                    }
                    //有多个店铺时
                    shopList.add(se.getShop());
                    request.getSession().setAttribute("shopList",shopList);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (ShopOperationException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg",e.getMessage());
            }

//            判断是不是在审核中，此刻的state应该为0

            return modelMap;
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺信息");
            return modelMap;
        }
    }
// -----------------改造前使用的方法---------------------------
//    由于CommonsMultipartFile与File类型不能强制转化
//    且因为在这里用File类型能够方便测试
//    写一个利用流来把CommonsMultipartFile的信息写入到File中去
//    private static void inputStreamToFile(InputStream ins, File file)
//    {
//        FileOutputStream os= null;
//        try {
//            os = new FileOutputStream(file);
//            int bytesRead = 0;
//            byte[] buffer = new byte[1024];
//            while ((bytesRead = ins.read(buffer)) != -1)
//            {
//                os.write(buffer,0,bytesRead);
//            }
//        } catch (IOException e) {
//            throw new ShopOperationException("调用inputStreamToFile产生异常"+e.getMessage());
//        } finally {
//            try {
//                if (ins != null)
//                {
//                    ins.close();
//                }
//                if (os != null)
//                {
//                    os.close();
//                }
//            } catch (Exception e) {
//                throw new ShopOperationException("调用inputStreamToFile关闭流失败"+e.getMessage());
//            }
//        }
//
//    }
}