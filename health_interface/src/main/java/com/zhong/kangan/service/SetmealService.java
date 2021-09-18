package com.zhong.kangan.service;

import com.zhong.kangan.common.pojo.Setmeal;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;

import java.util.List;

/**
 * @author 华韵流风
 * @ClassName SetmealService
 * @Date 2021/7/30 18:10
 * @packageName com.zhong.kangan.service
 * @Description TODO
 */
public interface SetmealService {


    /**
     * 查询所有套餐
     *
     * @param queryPageBean queryPageBean
     * @return PageResult
     */
    PageResult findAllSetmealByPages(QueryPageBean queryPageBean);


    /**
     * 新增套餐
     *
     * @param setmeal       setmeal
     * @param checkgroupIds checkgroupIds
     */
    void addSetmeal(Setmeal setmeal, int[] checkgroupIds);

    /**
     * 查询套餐
     *
     * @param id id
     * @return Setmeal
     */
    Setmeal findSetmeal(int id);

    /**
     * 查询中间表
     *
     * @param id id
     * @return int[]
     */
    int[] findSetmealCheckGroup(int id);

    /**
     * 修改套餐数据
     *
     * @param setmeal       setmeal
     * @param checkgroupIds checkgroupIds
     */
    void editSetmeal(Setmeal setmeal, int[] checkgroupIds);

    /**
     * 删除套餐
     *
     * @param id id
     */
    void deleteSetmeal(int id);


}
