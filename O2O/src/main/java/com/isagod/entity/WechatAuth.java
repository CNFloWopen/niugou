package com.isagod.entity;

import java.util.Date;

/***
 * 微信账号实体类
 * CREATE TABLE `tb_wechat_auth` (
 *   `wechat_auth_id` INT(10) NOT NULL AUTO_INCREMENT,
 *   `user_id` INT(10) NOT NULL,
 *   `open_id` VARCHAR(1024) NOT NULL,
 *   `create_time` DATETIME DEFAULT NULL,
 *   PRIMARY KEY (`wechat_auth_id`),
 *   CONSTRAINT `fk_wechatauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
 * ) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
 */
public class WechatAuth {
    private Long wechatAuthId;//微信id
    private String openId;//公众号要用
    private Date createTime;
    private PersonInfo personInfo;


    public Long getWechatAuthId() {
        return wechatAuthId;
    }

    public void setWechatAuthId(Long wechatAuthId) {
        this.wechatAuthId = wechatAuthId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }
}