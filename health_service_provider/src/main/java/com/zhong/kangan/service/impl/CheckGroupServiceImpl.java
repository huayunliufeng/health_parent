package com.zhong.kangan.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhong.kangan.common.pojo.CheckGroup;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;
import com.zhong.kangan.mapper.CheckGroupMapper;
import com.zhong.kangan.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 24804
 * @ClassName CheckGroupImpl
 * @Description TOD0
 * @Date 2021/7/28上午 06:58
 * @packageName cn.du.kangan.service.impl
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    @Override
    public PageResult findAllCheckGroupByPages(QueryPageBean queryPageBean) {
        //从queryPageBean取出数据
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //在执行查询之前，让分页插件进行工作
        PageHelper.startPage(currentPage,pageSize);
        //返回的是List,可是Page继承了ArrayList，所以可以强转
        Page<CheckGroup> allCheckGroupByPages = (Page<CheckGroup>) checkGroupMapper.findAllCheckGroupByPages(queryString);
        PageResult pageResult = new PageResult(allCheckGroupByPages.getTotal(),allCheckGroupByPages.getResult());
        return pageResult;
    }
}
