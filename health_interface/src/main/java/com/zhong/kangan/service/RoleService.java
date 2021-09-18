package com.zhong.kangan.service;

import com.zhong.kangan.common.pojo.Role;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;

/**
 * @author 华韵流风
 * @ClassName RoleService
 * @Date 2021/8/21 9:58
 * @packageName com.zhong.kangan.service
 * @Description TODO
 */
public interface RoleService {

    /**
     * 按条件查询角色
     *
     * @param queryPageBean queryPageBean
     * @return PageResult
     */
    PageResult findRoleByKey(QueryPageBean queryPageBean);

    /**
     * 根据id查询Role
     *
     * @param id id
     * @return Role
     */
    Role findRoleById(int id);

    /**
     * 查询permissionIds
     *
     * @param id id
     * @return int[]
     */
    int[] findPermissionIds(int id);

    /**
     * 查询Permission
     *
     * @param queryPageBean queryPageBean
     * @return PageResult
     */
    PageResult findPermission(QueryPageBean queryPageBean);

    /**
     * 添加角色
     *
     * @param role role
     * @param permissionIds permissionIds
     */
    void addRole(Role role,int[] permissionIds);

    /**
     * 修改角色
     *
     * @param role role
     * @param permissionIds permissionIds
     */
    void editRole(Role role,int[] permissionIds);

    /**
     * 删除角色
     *
     * @param id id
     */
    void deleteRole(int id);

}
