package com.order.processing.orderprocessing.service;

import com.order.processing.orderprocessing.entity.Order;

import java.util.List;

public interface OrderService {
    void processOrders(String filePath);
    List<Order> getAllOrders();
}
