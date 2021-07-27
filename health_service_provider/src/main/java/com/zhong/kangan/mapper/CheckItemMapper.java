package com.zhong.kangan.mapper;

import com.zhong.kangan.common.pojo.CheckItem;

import java.util.List;

/**
 * @author 华韵流风
 * @ClassName CheckItemMapper
 * @Description TODO
 * @Date 2021/7/24 15:57
 * @packageName com.zhong.kangan.mapper
 */
public interface CheckItemMapper {

    /**
     * 添加检查项
     *
     * @param checkItem checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 查询
     *
     * @param queryString queryString
     * @return List<CheckItem>
     */
    List<CheckItem> findCheckItemsByPageByCriteria(String queryString);

    /**
     * 查询检查项信息
     *
     * @param id id
     * @return CheckItem
     */
    CheckItem findById(int id);

    /**
     * 修改检查项
     *
     * @param checkItem checkItem
     */
    void edit(CheckItem checkItem);

    /**
     * 删除检查项
     *
     * @param id id
     */
    void delete(int id);
}
