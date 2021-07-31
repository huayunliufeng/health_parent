package com.zhong.kangan.mapper;

import com.zhong.kangan.common.pojo.Setmeal;

import java.util.List;

/**
 * @author 华韵流风
 * @ClassName SetmealMapper
 * @Date 2021/7/30 18:13
 * @packageName com.zhong.kangan.mapper
 * @Description TODO
 */
public interface SetmealMapper {

    /**
     * 查询所有套餐
     *
     * @param queryString queryString
     * @return PageResult
     */
    List<Setmeal> findAllSetmealByPages(String queryString);

    /**
     * 新增套餐
     *
     * @param setmeal setmeal
     */
    void addSetmeal(Setmeal setmeal);

    /**
     * 新增中间表数据
     *
     * @param id            id
     * @param checkgroupIds checkgroupIds
     */
    void addSetmealCheckGroup(int id, int[] checkgroupIds);

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
     * @param setmeal setmeal
     */
    void editSetmeal(Setmeal setmeal);

    /**
     * 删除中间表数据
     *
     * @param id id
     */
    void deleteSetmealCheckGroup(int id);

    /**
     * 删除套餐
     *
     * @param id id
     */
    void deleteSetmeal(int id);

}
