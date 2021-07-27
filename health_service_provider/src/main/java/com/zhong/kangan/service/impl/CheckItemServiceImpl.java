package com.zhong.kangan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhong.kangan.common.pojo.CheckItem;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;
import com.zhong.kangan.mapper.CheckItemMapper;
import com.zhong.kangan.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 华韵流风
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Date 2021/7/24 15:55
 * @packageName com.zhong.kangan.service.impl
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    //如果注入的bean在同一容器中，用@Autowired，如果是在服务端用@Reference

    @Autowired
    private CheckItemMapper checkItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(CheckItem checkItem) {
        checkItemMapper.add(checkItem);
    }

    @Override
    public PageResult findCheckItemsByPageByCriteria(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckItem> page = (Page<CheckItem>) checkItemMapper.findCheckItemsByPageByCriteria(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public CheckItem findById(int id) {
        return checkItemMapper.findById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(CheckItem checkItem) {
        checkItemMapper.edit(checkItem);
    }

    @Override
    public void delete(int id) {
        checkItemMapper.delete(id);
    }
}
