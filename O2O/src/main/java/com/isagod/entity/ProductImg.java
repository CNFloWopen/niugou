package com.isagod.entity;

import java.util.Date;

/**
 * 图片地址实体类
 * CREATE TABLE `tb_product_img` (
 *   `product_img_id` INT(20) NOT NULL AUTO_INCREMENT,
 *   `img_addr` VARCHAR(2000) NOT NULL,
 *   `img_desc` VARCHAR(2000) DEFAULT NULL,
 *   `priority` INT(2) DEFAULT '0',
 *   `create_time` DATETIME DEFAULT NULL,
 *   `product_id` INT(20) DEFAULT NULL,
 *   PRIMARY KEY (`product_img_id`),
 *   CONSTRAINT `fk_proimg_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`)
 * ) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 */
public class ProductImg {
    private Long productImgId;
    private String imgAddr;
    private String imgDesc;
    private Integer priority;
    private Date createTime;
    private Long productId;

    public Long getProductImgId() {
        return productImgId;
    }

    public void setProductImgId(Long productImgId) {
        this.productImgId = productImgId;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

}