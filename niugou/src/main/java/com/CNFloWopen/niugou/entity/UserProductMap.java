package com.CNFloWopen.niugou.entity;

import java.util.Date;
//顾客消费的商品映射
public class UserProductMap {
//	主键id
	private Long userProductId;
//	 创建时间
	private Date createTime;
//	顾客信息实体类
	private PersonInfo user;
//	商品信息实体类
	private Product product;
//	店铺实体类
	private Shop shop;
//	操作员信息实体类
	private PersonInfo operator;
	public Long getUserProductId() {
		return userProductId;
	}

	public void setUserProductId(Long userProductId) {
		this.userProductId = userProductId;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public PersonInfo getUser() {
		return user;
	}

	public void setUser(PersonInfo user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public PersonInfo getOperator() {
		return operator;
	}
	public void setOperator(PersonInfo operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "UserProductMap{" +
				"userProductId=" + userProductId +
				", createTime=" + createTime +
				", user=" + user +
				", product=" + product +
				", shop=" + shop +
				", operator=" + operator +
				'}';
	}
}
