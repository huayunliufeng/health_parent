package com.zhong.kangan.mapper;

import com.zhong.kangan.common.pojo.Setmeal;

import java.util.Date;
import java.util.List;

/**
 * @author 华韵流风
 * @ClassName ReportMapper
 * @Date 2021/8/25 20:35
 * @packageName com.zhong.kangan.mapper
 * @Description TODO
 */
public interface ReportMapper {
    /**
     * 查询每月的会员数
     *
     * @param year year
     * @param month month
     * @return int
     */
    int findMemberCountByYearAndMonth(int year,int month);

    /**
     * 查询各套餐预约的数量
     *
     * @param id id
     * @return int
     */
    int findOrderCountBySetId(int id);

    /**
     * 新增会员数
     *
     * @return int
     */
    Integer getTodayNewMember();

    /**
     * 总会员数
     *
     * @return int
     */
    Integer getTotalMember();

    /**
     * 本周新增会员数
     *
     * @return int
     */
    Integer getThisWeekNewMember();

    /**
     * 本月新增会员数
     *
     * @return int
     */
    Integer getThisMonthNewMember();

    /**
     * 今日预约数
     *
     * @return int
     */
    Integer getTodayOrderNumber();

    /**
     * 今日到诊数
     *
     * @return int
     */
    Integer getTodayVisitsNumber();

    /**
     * 本周预约数
     *
     * @return int
     */
    Integer getThisWeekOrderNumber();

    /**
     * 本周到诊数
     *
     * @return int
     */
    Integer getThisWeekVisitsNumber();

    /**
     * 本月预约数
     *
     * @return int
     */
    Integer getThisMonthOrderNumber();

    /**
     * 本月到诊数
     *
     * @return int
     */
    Integer getThisMonthVisitsNumber();

    /**
     * 获取热门套餐ID
     *
     * @return int[]
     */
    int[] getHotSetmealIds();

    /**
     * 查询所有预约数量
     *
     * @return int
     */
    Integer findAllCountOrder();
}
