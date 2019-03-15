package com.isagod.service.impl;

import com.isagod.dao.ShopDao;
import com.isagod.dto.ImageHolder;
import com.isagod.dto.ShopExecution;
import com.isagod.entity.Shop;
import com.isagod.enums.ShopStateEnum;
import com.isagod.exceptions.ShopOperationException;
import com.isagod.service.ShopService;
import com.isagod.util.ImageUtil;
import com.isagod.util.PageCalculatop;
import com.isagod.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;
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
//                    更新店铺
                    effect = shopDao.updateShop(shop);
                    if (effect<=0)
                    {
                        throw new ShopOperationException("更新失败");
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
