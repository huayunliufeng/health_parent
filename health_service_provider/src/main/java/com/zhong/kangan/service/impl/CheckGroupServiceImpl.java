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
import org.springframework.transaction.annotation.Transactional;

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
        //在执行查询之前，让分页插件进行工作
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //返回的是List,可是Page继承了ArrayList，所以可以强转
        Page<CheckGroup> allCheckGroupByPages = (Page<CheckGroup>) checkGroupMapper.findAllCheckGroupByPages(queryPageBean.getQueryString());
        return new PageResult(allCheckGroupByPages.getTotal(), allCheckGroupByPages.getResult());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(int id) {
        checkGroupMapper.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCheckGroup(CheckGroup checkGroup, int[] checkitemIds) {
        checkGroupMapper.addCheckGroup(checkGroup);
        int id = checkGroup.getId();
        addCheckGroupCheckItem(id, checkitemIds);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addCheckGroupCheckItem(int id, int[] checkitemIds) {
        if(checkitemIds.length!=0){
            checkGroupMapper.addCheckGroupCheckItem(id, checkitemIds);
        }
    }

    @Override
    public CheckGroup findCheckGroupById(int id) {
        return checkGroupMapper.findCheckGroupById(id);
    }

    @Override
    public int[] findCheckGroupCheckItemById(int id) {
        return checkGroupMapper.findCheckGroupCheckItemById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editCheckGroup(CheckGroup checkGroup, int[] checkitemIds) {
        checkGroupMapper.editCheckGroup(checkGroup);
        int id = checkGroup.getId();
        checkGroupMapper.deleteCheckGroupCheckItem(id);
        addCheckGroupCheckItem(id, checkitemIds);
    }
}
