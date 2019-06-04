package com.CNFloWopen.niugou.entity;

import java.util.Date;

/**
 * 店铺实体类
 * CREATE TABLE `tb_shop` (
 *   `shop_id` INT(10) NOT NULL AUTO_INCREMENT,
 *   `owner_id` INT(10) NOT NULL COMMENT '店铺创建人',
 *   `area_id` INT(5) DEFAULT NULL,
 *   `shop_category_id` INT(11) DEFAULT NULL,
 *   `shop_name` VARCHAR(256) NOT NULL,
 *   `shop_desc` VARCHAR(1024) DEFAULT NULL,
 *   `shop_addr` VARCHAR(200) DEFAULT NULL,
 *   `phone` VARCHAR(128) DEFAULT NULL,
 *   `shop_img` VARCHAR(1024) DEFAULT NULL,
 *   `priority` INT(3) DEFAULT '0',
 *   `create_time` DATETIME DEFAULT NULL,
 *   `last_edit_time` DATETIME DEFAULT NULL,
 *   `enable_status` INT(2) NOT NULL DEFAULT '0',
 *   `advice` VARCHAR(255) DEFAULT NULL,
 *   PRIMARY KEY (`shop_id`),
 *   CONSTRAINT `fk_shop_area` FOREIGN KEY (`area_id`) REFERENCES `tb_area` (`area_id`),
 *   CONSTRAINT `fk_shop_profile` FOREIGN KEY (`owner_id`) REFERENCES `tb_person_info` (`user_id`),
 *   CONSTRAINT `fk_shop_shopcate` FOREIGN KEY (`shop_category_id`) REFERENCES `tb_shop_category` (`shop_category_id`)
 * ) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 */
public class Shop {
    private Long shopId;
    private String shopName;
    private String shopDesc;
    private String shopAddr;
    private String phone;
    private String shopImg;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
//    去掉了2个字段
    private Integer enableStatus;
    private Area area;
    private PersonInfo owner;
    private ShopCategory shopCategory;


    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
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


    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public PersonInfo getOwner() {
        return owner;
    }

    public void setOwner(PersonInfo owner) {
        this.owner = owner;
    }

    public ShopCategory getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(ShopCategory shopCategory) {
        this.shopCategory = shopCategory;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "enableStatus=" + enableStatus +
                '}';
    }
}