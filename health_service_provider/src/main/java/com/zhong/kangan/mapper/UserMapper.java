package com.zhong.kangan.mapper;
import com.zhong.kangan.common.pojo.User;

import java.util.List;

/**
 * @author 华韵流风
 * @ClassName RoleMapper
 * @Date 2021/8/21 9:58
 * @packageName com.zhong.kangan.mapper
 * @Description TODO
 */
public interface UserMapper {
    /**
     * 按条件查询User
     *
     * @param key key
     * @return List<User>
     */
    List<User> findUserByKey(String key);

    /**
     * 根据id查询Role
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
     */
    void addUser(User user);

    /**
     * 添加中间表数据
     *
     * @param id id
     * @param roleIds roleIds
     */
    void addUserRole(int id,int[] roleIds);

    /**
     * 删除中间表数据
     *
     * @param id id
     */
    void deleteUserRole(int id);

    /**
     * 修改用户
     *
     * @param user user
     */
    void editUser(User user);

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
