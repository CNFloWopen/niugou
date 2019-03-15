package com.isagod.entity;

import java.util.Date;

/**
 * 店铺类别实体类
 * CREATE TABLE `tb_shop_category` (
 *   `shop_category_id` INT(11) NOT NULL AUTO_INCREMENT,
 *   `shop_category_name` VARCHAR(100) NOT NULL DEFAULT '',
 *   `shop_category_desc` VARCHAR(1000) DEFAULT '',
 *   `shop_category_img` VARCHAR(2000) DEFAULT NULL,
 *   `priority` INT(2) NOT NULL DEFAULT '0',
 *   `create_time` DATETIME DEFAULT NULL,
 *   `last_edit_time` DATETIME DEFAULT NULL,
 *   `parent_id` INT(11) DEFAULT NULL,
 *   PRIMARY KEY (`shop_category_id`),
 *   CONSTRAINT `fk_shop_category_self` FOREIGN KEY (`parent_id`) REFERENCES `tb_shop_category` (`shop_category_id`)
 * ) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 */
public class ShopCategory {
    private Long shopCategoryId;//店铺id
    private String shopCategoryName;//店铺名
    private String shopCategoryDesc;//店铺描述
    private String shopCategoryImg;//店铺图片
    private Integer priority;//权重
    private Date createTime;
    private Date lastEditTime;
    //上级id
    private ShopCategory parent;

    public Long getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(Long shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public String getShopCategoryName() {
        return shopCategoryName;
    }

    public void setShopCategoryName(String shopCategoryName) {
        this.shopCategoryName = shopCategoryName;
    }

    public String getShopCategoryDesc() {
        return shopCategoryDesc;
    }

    public void setShopCategoryDesc(String shopCategoryDesc) {
        this.shopCategoryDesc = shopCategoryDesc;
    }

    public String getShopCategoryImg() {
        return shopCategoryImg;
    }

    public void setShopCategoryImg(String shopCategoryImg) {
        this.shopCategoryImg = shopCategoryImg;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public ShopCategory getParent() {
        return parent;
    }

    public void setParent(ShopCategory parent) {
        this.parent = parent;
    }
}