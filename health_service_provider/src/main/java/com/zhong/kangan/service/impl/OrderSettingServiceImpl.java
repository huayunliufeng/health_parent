package com.zhong.kangan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zhong.kangan.common.pojo.OrderSetting;
import com.zhong.kangan.mapper.OrderSettingMapper;
import com.zhong.kangan.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 华韵流风
 * @ClassName OrderSettingServiceImpl
 * @Date 2021/8/10 17:55
 * @packageName com.zhong.kangan.service.impl
 * @Description TODO
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(List<OrderSetting> orderSettings) {
        if (orderSettings.size() != 0) {
            orderSettingMapper.add(orderSettings);
        }
    }

    @Override
    public int findOrderSettingByDate(Date orderDate) {
        return orderSettingMapper.findOrderSettingByDate(orderDate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByOrderDate(OrderSetting orderSetting) {
        orderSettingMapper.updateByOrderDate(orderSetting);
    }

    @Override
    public List<OrderSetting> findOrderSettingByMonth(String year, String month) {
        return orderSettingMapper.findOrderSettingByMonth(year, month);
    }
}
