package com.zhong.kangan.service;


import com.zhong.kangan.common.pojo.CheckGroup;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;

/**
 * @author 24804
 * @ClassName CheckGroupService
 * @Description TOD0
 * @Date 2021/7/28上午 06:35
 * @packageName cn.du.kangan.service
 */
public interface CheckGroupService {

    /**
     * 分页
     * @param queryPageBean queryPageBean
     * @return PageResult
     */
   PageResult findAllCheckGroupByPages(QueryPageBean queryPageBean);


    /**
     * 删除
     *
     * @param id id
     */
    void delete(int id);

    /**
     * 添加检查组
     *
     * @param checkGroup checkGroup
     * @param checkitemIds checkitemIds
     */
    void addCheckGroup(CheckGroup checkGroup,int[] checkitemIds);

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
     * @param checkitemIds checkitemIds
     */
    void editCheckGroup(CheckGroup checkGroup,int[] checkitemIds);
}
