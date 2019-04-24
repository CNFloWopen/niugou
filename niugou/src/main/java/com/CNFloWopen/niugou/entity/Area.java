package com.CNFloWopen.niugou.entity;

import java.util.Date;

/***
 * 区域实体类
 *CREATE TABLE `tb_area` (
 *   `area_id` INT(2) NOT NULL AUTO_INCREMENT,
 *   `area_name` VARCHAR(200) NOT NULL,
 *   `priority` INT(2) NOT NULL DEFAULT '0',
 *   `create_time` DATETIME DEFAULT NULL,
 *   `last_edit_time` DATETIME DEFAULT NULL,
 *   PRIMARY KEY(`area_id`),
 *   UNIQUE KEY `UK_AREA`(`area_name`)
 * )ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 */
public class Area {
    //区域id
    private Integer areaId;
    //区域名
    private String areaName;
    //权重，优先级
    private Integer priority;
    //创建时间
    private Date createTime;
    //更新时间
    private Date lastEditTime;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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


    @Override
    public String toString() {
        return "Area{" +
                "areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                '}';
    }
}