package com.CNFloWopen.niugou.entity;

import java.util.Date;

/**
 * 头条实体类
 * CREATE TABLE `tb_head_line` (
 *   `line_id` INT(100) NOT NULL AUTO_INCREMENT,
 *   `line_name` VARCHAR(1000) DEFAULT NULL,
 *   `line_link` VARCHAR(2000) NOT NULL,
 *   `line_img` VARCHAR(2000) NOT NULL,
 *   `priority` INT(2) DEFAULT NULL,
 *   `enable_status` INT(2) NOT NULL DEFAULT '0',
 *   `create_time` DATETIME DEFAULT NULL,
 *   `last_edit_time` DATETIME DEFAULT NULL,
 *   PRIMARY KEY (`line_id`)
 * ) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 */
public class HeadLine {
    private Long lineId; //头条id
    private String lineName; //头条名
    private String lineLink; //头条连接
    private String lineImg; //头条图片
    private Integer priority; //权重--优先级
    //状态 0:不可用 1:可用
    private Integer enableStatus;
    private Date createTime;
    private Date lastEditTime;

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineLink() {
        return lineLink;
    }

    public void setLineLink(String lineLink) {
        this.lineLink = lineLink;
    }

    public String getLineImg() {
        return lineImg;
    }

    public void setLineImg(String lineImg) {
        this.lineImg = lineImg;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
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

}