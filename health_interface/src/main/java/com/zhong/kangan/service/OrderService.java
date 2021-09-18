package com.zhong.kangan.service;

import com.zhong.kangan.common.pojo.*;
import org.aspectj.weaver.ast.Or;

import java.util.Date;
import java.util.List;

/**
 * @author 华韵流风
 * @ClassName OrderService
 * @Date 2021/8/13 20:58
 * @packageName com.zhong.kangan.service
 * @Description TODO
 */
public interface OrderService {

    /**
     * 前端查询的套餐
     *
     * @return List<Setmeal>
     */
    List<Setmeal> findAllSetmeal();

    /**
     * 根据id查询Setmeal
     *
     * @param id id
     * @return Setmeal
     */
    Setmeal findSetmealById(int id);

    /**
     * 添加预约
     *
     * @param order order
     * @return int int
     */
    int addOrder(Order order);

    /**
     * 获取预约人数
     *
     * @param orderDate orderDate
     * @return OrderSetting
     */
    OrderSetting getResNum(Date orderDate);

    /**
     * 根据memberId查询订单
     *
     * @param memberId memberId
     * @return Order
     */
    List<Order> getOrderByMemId(int memberId);

    /**
     * 根据id查询订单
     *
     * @param id id
     * @return Order
     */
    Order getOrderById(int id);

    /**
     * 查询所有订单
     *
     * @return List<Order>
     */
    List<Order> findAllOrder();





}
