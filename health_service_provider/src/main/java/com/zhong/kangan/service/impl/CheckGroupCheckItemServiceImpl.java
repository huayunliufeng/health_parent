package com.zhong.kangan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zhong.kangan.mapper.CheckGroupCheckItemMapper;
import com.zhong.kangan.service.CheckGroupCheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 华韵流风
 * @ClassName CheckGroupCheckItemServiceImpl
 * @Date 2021/7/27 16:23
 * @packageName com.zhong.kangan.service.impl
 * @Description TODO
 */
@Service(interfaceClass = CheckGroupCheckItemService.class)
public class CheckGroupCheckItemServiceImpl implements CheckGroupCheckItemService{

    @Autowired
    private CheckGroupCheckItemMapper cgciMapper;

    @Override
    public int findCountByCheckItemId(int id) {
        return cgciMapper.findCountByCheckItemId(id);
    }
}
