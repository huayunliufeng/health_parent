package com.zhong.kangan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhong.kangan.common.pojo.Permission;
import com.zhong.kangan.common.pojo.Role;
import com.zhong.kangan.common.pojo.User;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;
import com.zhong.kangan.mapper.RoleMapper;
import com.zhong.kangan.mapper.UserMapper;
import com.zhong.kangan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 华韵流风
 * @ClassName UserServiceImpl
 * @Date 2021/8/21 10:02
 * @packageName com.zhong.kangan.service.impl
 * @Description TODO
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }

    @Override
    public PageResult findUserByKey(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<User> page = (Page<User>) userMapper.findUserByKey(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public int[] findRoleIds(int id) {
        return userMapper.findRoleIds(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(User user, int[] roleIds) {
        userMapper.addUser(user);
        if (roleIds.length != 0 && roleIds != null) {
            userMapper.addUserRole(user.getId(), roleIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editUser(User user, int[] roleIds) {
        userMapper.editUser(user);
        userMapper.deleteUserRole(user.getId());
        if (roleIds.length != 0 && roleIds != null) {
            userMapper.addUserRole(user.getId(), roleIds);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(int id) {
        userMapper.deleteUserRole(id);
        userMapper.deleteUser(id);
    }

    @Override
    public User findUserByUserName(String username) {
        User user = userMapper.findUserByUserName(username);
        if (user == null) {
            return null;
        }
        Set<Role> roles = new HashSet<>();
        int[] roleIds = userMapper.findRoleIds(user.getId());
        for (int roleId : roleIds) {
            Role role = roleMapper.findRoleById(roleId);
            Set<Permission> permissions = new HashSet<>();
            int[] permissionIds = roleMapper.findPermissionIds(role.getId());
            for (int permissionId : permissionIds) {
                Permission permission = roleMapper.findPermissionById(permissionId);
                permissions.add(permission);
            }
            role.setPermissions(permissions);
            roles.add(role);
        }
        user.setRoles(roles);
        return user;
    }
}

