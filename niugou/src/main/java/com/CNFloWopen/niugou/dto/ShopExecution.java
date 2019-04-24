package com.CNFloWopen.niugou.dto;



import com.CNFloWopen.niugou.entity.Shop;
import com.CNFloWopen.niugou.enums.ShopStateEnum;

import java.util.List;

/**
 * 进行操作的结果
 */
public class ShopExecution {
    //操作的店铺(增删查改时候用到)
    private Shop shop;
    //结果状态
    private int state;
    //店铺数量
    private int count;
    //状态标识
    private String stateInfo;
    //shop电路列表
    private List<Shop> shopList;

    public ShopExecution() {
    }

    //店铺操作失败的时候使用的构造器
    public ShopExecution(ShopStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //店铺操作成功的时候使用的构造器
    public ShopExecution(ShopStateEnum stateEnum,Shop shop)
    {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = shop;
    }

    //店铺操作成功的时候使用的构造器
    public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList)
    {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
    }


    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}