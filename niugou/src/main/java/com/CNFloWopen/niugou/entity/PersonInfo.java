package com.CNFloWopen.niugou.entity;

import java.util.Date;

/**
 * 用户实体类
 *CREATE TABLE `tb_person_info` (
 *   `user_id` int(10) NOT NULL AUTO_INCREMENT,
 *   `name` varchar(32) DEFAULT NULL,
 *   `profile_img` varchar(1024) DEFAULT NULL,
 *   `email` varchar(1024) DEFAULT NULL,
 *   `gender` varchar(2) DEFAULT NULL,
 *   `enable_status` int(2) NOT NULL DEFAULT '0' COMMENT '0:禁止使用本商城,1:允许使用本商城',
 *   `user_type` int(2) NOT NULL DEFAULT '1' COMMENT '1:顾客,2:店家,3:超级管理员',
 *   `create_time` datetime DEFAULT NULL,
 *   `last_edit_time` datetime DEFAULT NULL,
 *   PRIMARY KEY (`user_id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8
 */
public class PersonInfo {
    private Long userId;//用户ID
    private String name;//用户姓名
    private String profileImg;//用户头像
    private String email;//用户邮箱
    private String gender;//用户性别
    private Integer enableStatus;//用户状态
    //身份标识 1:顾客 2:店家
    private Integer userType;
    private Date createTime;
    private Date lastEditTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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