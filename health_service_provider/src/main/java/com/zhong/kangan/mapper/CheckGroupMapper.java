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
     *查找数据
     * @param queryString
     * @return
     */
    List<CheckGroup> findAllCheckGroupByPages(String queryString);
}
