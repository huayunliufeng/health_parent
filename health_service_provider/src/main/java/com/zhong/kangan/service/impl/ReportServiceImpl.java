package com.zhong.kangan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zhong.kangan.common.pojo.Setmeal;
import com.zhong.kangan.mapper.ReportMapper;
import com.zhong.kangan.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 华韵流风
 * @ClassName ReportServiceImpl
 * @Date 2021/8/25 20:34
 * @packageName com.zhong.kangan.service.impl
 * @Description TODO
 */
@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {


    @Autowired
    private ReportMapper reportMapper;

    @Override
    public int findMemberCountByYearAndMonth(int year, int month) {
        return reportMapper.findMemberCountByYearAndMonth(year, month);
    }

    @Override
    public int findOrderCountBySetId(int id) {
        return reportMapper.findOrderCountBySetId(id);
    }

    @Override
    public Integer getTodayNewMember() {
        Integer count = reportMapper.getTodayNewMember();
        return count == null ? 0 : count;
    }

    @Override
    public Integer getTotalMember() {
        Integer count = reportMapper.getTotalMember();
        return count == null ? 0 : count;
    }

    @Override
    public Integer getThisWeekNewMember() {
        Integer count = reportMapper.getThisWeekNewMember();
        return count == null ? 0 : count;
    }

    @Override
    public Integer getThisMonthNewMember() {
        Integer count = reportMapper.getThisMonthNewMember();
        return count == null ? 0 : count;
    }

    @Override
    public Integer getTodayOrderNumber() {
        Integer count = reportMapper.getTodayOrderNumber();
        return count == null ? 0 : count;
    }

    @Override
    public Integer getTodayVisitsNumber() {
        Integer count = reportMapper.getTodayVisitsNumber();
        return count == null ? 0 : count;
    }

    @Override
    public Integer getThisWeekOrderNumber() {
        Integer count = reportMapper.getThisWeekOrderNumber();
        return count == null ? 0 : count;
    }

    @Override
    public Integer getThisWeekVisitsNumber() {
        Integer count = reportMapper.getThisWeekVisitsNumber();
        return count == null ? 0 : count;
    }

    @Override
    public Integer getThisMonthOrderNumber() {
        Integer count = reportMapper.getThisMonthOrderNumber();
        return count == null ? 0 : count;
    }

    @Override
    public Integer getThisMonthVisitsNumber() {
        Integer count = reportMapper.getThisMonthVisitsNumber();
        return count == null ? 0 : count;
    }

    @Override
    public int[] getHotSetmealIds() {
        return reportMapper.getHotSetmealIds();
    }

    @Override
    public Integer findAllCountOrder() {
        Integer count = reportMapper.findAllCountOrder();
        return count == null ? 0 : count;
    }
}
