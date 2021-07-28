package com.zhong.kangan.service;


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
     * @return
     */
   PageResult findAllCheckGroupByPages(QueryPageBean queryPageBean);
}
