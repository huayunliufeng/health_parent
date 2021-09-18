package com.zhong.kangan.service;

import com.zhong.kangan.common.pojo.Role;
import com.zhong.kangan.common.pojo.User;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;

/**
 * @author 华韵流风
 * @ClassName UserService
 * @Date 2021/8/21 10:01
 * @packageName com.zhong.kangan.service
 * @Description TODO
 */
public interface UserService {
    /**
     * 按条件查询用户
     *
     * @param queryPageBean queryPageBean
     * @return PageResult
     */
    PageResult findUserByKey(QueryPageBean queryPageBean);

    /**
     * 根据id查询User
     *
     * @param id id
     * @return User
     */
    User findUserById(int id);

    /**
     * 查询roleIds
     *
     * @param id id
     * @return int[]
     */
    int[] findRoleIds(int id);

    /**
     * 添加用户
     *
     * @param user user
     * @param roleIds roleIds
     */
    void addUser(User user, int[] roleIds);

    /**
     * 修改用户
     *
     * @param user user
     * @param roleIds roleIds
     */
    void editUser(User user,int[] roleIds);

    /**
     * 删除用户
     *
     * @param id id
     */
    void deleteUser(int id);

    /**
     * 按用户名查询用户
     *
     * @param username username
     * @return User
     */
    User findUserByUserName(String username);
}
