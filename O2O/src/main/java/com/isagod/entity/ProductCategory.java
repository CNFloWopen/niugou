package com.isagod.entity;

import java.util.Date;

/**
 * 商品类别实体类
 * CREATE TABLE `tb_product_category` (
 *   `product_category_id` INT(11) NOT NULL AUTO_INCREMENT,
 *   `product_category_name` VARCHAR(100) NOT NULL,
 *   `priority` INT(2) DEFAULT '0',
 *   `create_time` DATETIME DEFAULT NULL,
 *   `shop_id` INT(20) NOT NULL DEFAULT '0',
 *   PRIMARY KEY (`product_category_id`),
 *   CONSTRAINT `fk_procate_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
 * ) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 */
public class ProductCategory {
    private Long productCategoryId;
    private Long shopId;
    private String productCategoryName;
    private Integer priority;
    private Date createTime;

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
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
}