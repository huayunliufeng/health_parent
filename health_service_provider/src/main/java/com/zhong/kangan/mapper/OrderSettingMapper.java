package com.zhong.kangan.mapper;

import com.zhong.kangan.common.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

/**
 * @author 华韵流风
 * @ClassName OrderSettingMapper
 * @Date 2021/8/10 17:55
 * @packageName com.zhong.kangan.mapper
 * @Description TODO
 */
public interface OrderSettingMapper {

    /**
     * 添加预约设置
     *
     * @param orderSettings orderSettings
     */
    void add(List<OrderSetting> orderSettings);

    /**
     * 获取指定月份的预约设置
     *
     * @param year year
     * @param month month
     * @return List<OrderSetting>
     */
    List<OrderSetting> findOrderSettingByMonth(String year,String month);

    /**
     * 按日期查询
     *
     * @param orderDate orderDate
     * @return int
     */
    int findOrderSettingByDate(Date orderDate);

    /**
     * 更新预约人数
     *
     * @param orderSetting orderSetting
     */
    void updateByOrderDate(OrderSetting orderSetting);

}
