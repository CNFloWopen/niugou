package com.CNFloWopen.niugou.dao;

import com.CNFloWopen.niugou.entity.Order;

import java.util.List;

public interface OrderDao {
    void addOrder(Order order);
    List<Order> findOrders(long id);
}
