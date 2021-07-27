package com.zhong.kangan.mapper;

/**
 * @author 华韵流风
 * @ClassName CheckGroupCheckItemService
 * @Date 2021/7/27 16:19
 * @packageName com.zhong.kangan.service
 * @Description TODO
 */
public interface CheckGroupCheckItemMapper {

    /**
     * 查询检查项所在的组
     *
     * @param id id
     * @return int
     */
    int findCountByCheckItemId(int id);
}
