package com.CNFloWopen.niugou.service.impl;


import com.CNFloWopen.niugou.dao.ShopAuthMapDao;
import com.CNFloWopen.niugou.dao.ShopDao;
import com.CNFloWopen.niugou.dto.ImageHolder;
import com.CNFloWopen.niugou.dto.ShopExecution;
import com.CNFloWopen.niugou.entity.Shop;
import com.CNFloWopen.niugou.entity.ShopAuthMap;
import com.CNFloWopen.niugou.enums.ShopStateEnum;
import com.CNFloWopen.niugou.exceptions.ShopOperationException;
import com.CNFloWopen.niugou.service.ShopService;
import com.CNFloWopen.niugou.util.ImageUtil;
import com.CNFloWopen.niugou.util.PageCalculatop;
import com.CNFloWopen.niugou.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    @Autowired
    private ShopAuthMapDao shopAuthMapDao;

    @Override
    /**
     * 店铺注册
     */
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
        //空值判断
        if (shop == null)
        {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }

        try {
//            给店铺赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
//            添加店铺信息
            int effect = shopDao.insertShop(shop);
            if (effect<=0)
            {
                throw new ShopOperationException("创建店铺失败");
            }else {
                if (thumbnail.getImage()!=null)
                {
                    try {
//                    存储图片
                        addShopImg(shop,thumbnail);
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg error"+e.getMessage());
                    }
//                    更新店铺的图片地址
                    effect = shopDao.updateShop(shop);
                    if (effect<=0)
                    {
                        throw new ShopOperationException("更新图片地址失败");
                    }
//                    执行增加shopAuthMap的操作
//                    创建店铺的时候默认创建店家角色
                    ShopAuthMap shopAuthMap = new ShopAuthMap();
                    shopAuthMap.setEmployee(shop.getOwner());
                    shopAuthMap.setShop(shop);
                    shopAuthMap.setTitle("店家");
                    shopAuthMap.setTitleFlag(0);
                    shopAuthMap.setCreateTime(new Date());
                    shopAuthMap.setLastEditTime(new Date());
                    shopAuthMap.setEnableStatus(1);
                    try {
                        effect = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
                        if (effect<=0)
                        {
                            throw new ShopOperationException("授权失败");
                        }
                    }catch (Exception e)
                    {
                        throw new ShopOperationException("insertShopAuthMap error:"+e.getMessage());
                    }

                }
            }


        } catch (Exception e) {
            throw new ShopOperationException("addShop error"+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    @Override
    /**
     * 根据id获取店铺的信息
     */
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    /**
     * 修改店铺信息
     */
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) {
//        1.对图片处理
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        } else {
            //1.判断是否真的需要图片
            try {
                if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, thumbnail);
                }
                //        2.对店铺的处理
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            }catch (Exception e){
                throw new ShopOperationException("modifyShop error"+e.getMessage());
            }
        }

    }

    @Override
    /**
     * 店铺列表分页功能
     */
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        //获取的是页数，由于rowIndex获取的是从哪条开始，将页码转化为行码
        int rowIndex = PageCalculatop.calculateRowIndex(pageIndex,pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if (shopList!=null)
        {
            se.setShopList(shopList);
            se.setCount(count);
        }else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    private void addShopImg(Shop shop, ImageHolder thumbnail) {
//          获取图片shopImg的相对路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail,dest);
        shop.setShopImg(shopImgAddr);
    }
}
