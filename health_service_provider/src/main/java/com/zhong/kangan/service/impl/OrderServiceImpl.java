package com.zhong.kangan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zhong.kangan.common.pojo.*;
import com.zhong.kangan.mapper.OrderMapper;
import com.zhong.kangan.mapper.SetmealMapper;
import com.zhong.kangan.service.OrderService;
import com.zhong.kangan.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 华韵流风
 * @ClassName OrderServiceImpl
 * @Date 2021/8/13 21:00
 * @packageName com.zhong.kangan.service.impl
 * @Description TODO
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Setmeal> findAllSetmeal() {
        return orderMapper.findAllSetmeal();
    }

    @Override
    public Setmeal findSetmealById(int id) {
        return orderMapper.findSetmealById(id);
    }


    @Override
    public OrderSetting getResNum(Date orderDate) {
        return orderMapper.getResNum(orderDate);
    }

    @Override
    public List<Order> getOrderByMemId(int memberId) {
        return orderMapper.getOrderByMemId(memberId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addOrder(Order order) {
        orderMapper.addOrder(order);
        orderMapper.updateOrderSetting(order.getOrderDate());
        return order.getId();
    }

    @Override
    public List<Order> findAllOrder() {
        return orderMapper.findAllOrder();
    }

    @Override
    public Order getOrderById(int id) {
        return orderMapper.getOrderById(id);
    }
}
