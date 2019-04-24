package com.CNFloWopen.niugou.entity;

import java.util.Date;
import java.util.List;

/**
 * 商品实体类
 * CREATE TABLE `tb_product` (
 *   `product_id` INT(100) NOT NULL AUTO_INCREMENT,
 *   `product_name` VARCHAR(100) NOT NULL,
 *   `product_desc` VARCHAR(2000) DEFAULT NULL,
 *   `img_addr` VARCHAR(2000) DEFAULT '',
 *   `normal_price` VARCHAR(100) DEFAULT NULL,
 *   `promotion_price` VARCHAR(100) DEFAULT NULL,
 *   `priority` INT(2) NOT NULL DEFAULT '0',
 *   `create_time` DATETIME DEFAULT NULL,
 *   `last_edit_time` DATETIME DEFAULT NULL,
 *   `enable_status` INT(2) NOT NULL DEFAULT '0',
 *   `point` INT(10) DEFAULT NULL,
 *   `product_category_id` INT(11) DEFAULT NULL,
 *   `shop_id` INT(20) NOT NULL DEFAULT '0',
 *   PRIMARY KEY (`product_id`),
 *   CONSTRAINT `fk_product_procate` FOREIGN KEY (`product_category_id`) REFERENCES `tb_product_category` (`product_category_id`),
 *   CONSTRAINT `fk_product_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
 * ) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 */
public class Product {
    private Long productId;
    private String productName; //商品名称
    private String productDesc; //商品描述
    private String imgAddr;// 简略图
    private String normalPrice; //正常价
    private String promotionPrice; //折扣价
    private Integer priority; //权重
    private Date createTime;
    private Date lastEditTime;
    //0:下架 1:在前端展示
    private Integer enableStatus;
    private Integer point;
    private List<ProductImg> productImgList;
    private ProductCategory productCategory;
    private Shop shop;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public String getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice;
    }

    public String getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(String promotionPrice) {
        this.promotionPrice = promotionPrice;
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

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public List<ProductImg> getProductImgList() {
        return productImgList;
    }

    public void setProductImgList(List<ProductImg> productImgList) {
        this.productImgList = productImgList;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                '}';
    }
}