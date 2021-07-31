package com.zhong.kangan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhong.kangan.common.pojo.Setmeal;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;
import com.zhong.kangan.mapper.SetmealMapper;
import com.zhong.kangan.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 华韵流风
 * @ClassName SetmealServiceImpl
 * @Date 2021/7/30 18:14
 * @packageName com.zhong.kangan.service.impl
 * @Description TODO
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public PageResult findAllSetmealByPages(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Setmeal> setmeal = (Page<Setmeal>) setmealMapper.findAllSetmealByPages(queryPageBean.getQueryString());
        return new PageResult(setmeal.getTotal(), setmeal.getResult());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSetmeal(Setmeal setmeal, int[] checkgroupIds) {
        setmealMapper.addSetmeal(setmeal);
        if (checkgroupIds.length != 0) {
            setmealMapper.addSetmealCheckGroup(setmeal.getId(), checkgroupIds);
        }
    }

    @Override
    public Setmeal findSetmeal(int id) {
        return setmealMapper.findSetmeal(id);
    }

    @Override
    public int[] findSetmealCheckGroup(int id) {
        return setmealMapper.findSetmealCheckGroup(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editSetmeal(Setmeal setmeal, int[] checkgroupIds) {
        setmealMapper.editSetmeal(setmeal);
        setmealMapper.deleteSetmealCheckGroup(setmeal.getId());
        if (checkgroupIds.length != 0) {
            setmealMapper.addSetmealCheckGroup(setmeal.getId(), checkgroupIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSetmeal(int id) {
        setmealMapper.deleteSetmealCheckGroup(id);
        setmealMapper.deleteSetmeal(id);
    }
}
