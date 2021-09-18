package com.zhong.kangan.mapper;

import com.zhong.kangan.common.pojo.Permission;
import com.zhong.kangan.common.pojo.Role;
import com.zhong.kangan.common.result.PageResult;

import java.util.List;
import java.util.Set;

/**
 * @author 华韵流风
 * @ClassName RoleMapper
 * @Date 2021/8/21 9:58
 * @packageName com.zhong.kangan.mapper
 * @Description TODO
 */
public interface RoleMapper {

    /**
     * 按条件查询Role
     *
     * @param key key
     * @return List<Role>
     */
    List<Role> findRoleByKey(String key);

    /**
     * 根据id查询Role
     *
     * @param id id
     * @return Role
     */
    Role findRoleById(int id);

    /**
     * 查询Permission
     *
     * @return Set<Permission>
     */
    List<Permission> findPermission();

    /**
     * 根据id查询权限
     *
     * @param id id
     * @return Permission
     */
    Permission findPermissionById(int id);

    /**
     * 查询permissionIds
     *
     * @param id id
     * @return int[]
     */
    int[] findPermissionIds(int id);

    /**
     * 添加角色
     *
     * @param role role
     */
    void addRole(Role role);

    /**
     * 添加中间表数据
     *
     * @param id id
     * @param permissionIds permissionIds
     */
    void addRolePermission(int id,int[] permissionIds);

    /**
     * 删除中间表数据
     *
     * @param id id
     */
    void deleteRolePermission(int id);

    /**
     * 修改角色
     *
     * @param role role
     */
    void editRole(Role role);

    /**
     * 删除角色
     *
     * @param id id
     */
    void deleteRole(int id);

    /**
     * 删除中间表
     *
     * @param id id
     */
    void deleteUserRole(int id);

}
