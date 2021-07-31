package com.zhong.kangan.mapper;


import com.zhong.kangan.common.pojo.CheckGroup;

import java.util.List;

/**
 * @author 24804
 * @ClassName CheckGroupMapper
 * @Description TOD0
 * @Date 2021/7/28上午 06:56
 * @packageName cn.du.kangan.mapper
 */
public interface CheckGroupMapper {

    /**
     * 查找数据
     *
     * @param queryString queryString
     * @return List<CheckGroup>
     */
    List<CheckGroup> findAllCheckGroupByPages(String queryString);


    /**
     * 删除检查组
     *
     * @param id id
     */
    void delete(int id);

    /**
     * 添加检查组
     *
     * @param checkGroup checkGroup
     */
    void addCheckGroup(CheckGroup checkGroup);

    /**
     * 添加中间表数据
     *
     * @param id           id
     * @param checkitemIds checkitemIds
     */
    void addCheckGroupCheckItem(int id, int[] checkitemIds);

    /**
     * 查询检查组
     *
     * @param id id
     * @return CheckGroup
     */
    CheckGroup findCheckGroupById(int id);

    /**
     * 查询中间表
     *
     * @param id id
     * @return int[]
     */
    int[] findCheckGroupCheckItemById(int id);

    /**
     * 修改检查组
     *
     * @param checkGroup checkGroup
     */
    void editCheckGroup(CheckGroup checkGroup);

    /**
     * 删除中间表数据
     *
     * @param id id
     */
    void deleteCheckGroupCheckItem(int id);
}
