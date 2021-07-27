package com.zhong.kangan.service;

import com.zhong.kangan.common.pojo.CheckItem;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;

import java.util.List;

/**
 * @author 华韵流风
 * @ClassName CheckItemService
 * @Description TODO
 * @Date 2021/7/24 15:53
 * @packageName com.zhong.kangan.service
 */
public interface CheckItemService {

    /**
     * 添加检查项
     *
     * @param checkItem checkItem
     */
    void add(CheckItem checkItem);
    /**
     * 查询
     *
     * @param queryPageBean queryPageBean
     * @return List<CheckItem>
     */
    PageResult findCheckItemsByPageByCriteria(QueryPageBean queryPageBean);

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
