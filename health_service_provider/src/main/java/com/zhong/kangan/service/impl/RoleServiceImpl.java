package com.zhong.kangan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhong.kangan.common.pojo.Permission;
import com.zhong.kangan.common.pojo.Role;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;
import com.zhong.kangan.mapper.RoleMapper;
import com.zhong.kangan.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 华韵流风
 * @ClassName RoleServiceImpl
 * @Date 2021/8/21 9:59
 * @packageName com.zhong.kangan.service.impl
 * @Description TODO
 */
@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role findRoleById(int id) {
        return roleMapper.findRoleById(id);
    }

    @Override
    public PageResult findRoleByKey(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Role> page = (Page<Role>) roleMapper.findRoleByKey(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public int[] findPermissionIds(int id) {
        return roleMapper.findPermissionIds(id);
    }

    @Override
    public PageResult findPermission(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Permission> page = (Page<Permission>) roleMapper.findPermission();
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(Role role, int[] permissionIds) {
        roleMapper.addRole(role);
        if (permissionIds.length != 0 && permissionIds != null) {
            roleMapper.addRolePermission(role.getId(), permissionIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editRole(Role role, int[] permissionIds) {
        roleMapper.editRole(role);
        roleMapper.deleteRolePermission(role.getId());
        if (permissionIds.length != 0 && permissionIds != null) {
            roleMapper.addRolePermission(role.getId(), permissionIds);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(int id) {
        roleMapper.deleteRolePermission(id);
        roleMapper.deleteUserRole(id);
        roleMapper.deleteRole(id);
    }
}
