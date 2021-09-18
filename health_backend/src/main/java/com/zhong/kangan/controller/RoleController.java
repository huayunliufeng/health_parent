package com.zhong.kangan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhong.kangan.common.pojo.Role;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;
import com.zhong.kangan.common.result.Result;
import com.zhong.kangan.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 华韵流风
 * @ClassName RoleController
 * @Date 2021/8/21 9:54
 * @packageName com.zhong.kangan.controller
 * @Description TODO
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;

    @PreAuthorize("hasAnyAuthority('ROLE_QUERY')")
    @PostMapping("/page")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

        try {
            return roleService.findRoleByKey(queryPageBean);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("分页查询失败！");
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_QUERY')")
    @PostMapping("/perPage")
    public PageResult findPerPage(@RequestBody QueryPageBean queryPageBean){
        try {
            return roleService.findPermission(queryPageBean);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("分页查询失败！");
        }

    }

    @PreAuthorize("hasAnyAuthority('ROLE_QUERY','ROLE_EDIT')")
    @GetMapping("/update")
    public Result getRoleById(@RequestParam("id") int id) {
        Object[] objects = new Object[2];
        try {
            Object res1 = getRole(id);
            Object res2 = getPermissionIds(id);
            objects[0] = res1;
            objects[1] = res2;
            return new Result(true, "", objects);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "查询失败");
    }

    private Object getRole(int id) {
        return roleService.findRoleById(id);
    }

    private Object getPermissionIds(int id) {
        return roleService.findPermissionIds(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADD')")
    @PostMapping(value = "/add")
    public Result addRole(@RequestBody Role role, int[] permissionIds) {
        try {
            roleService.addRole(role, permissionIds);
            return new Result(true, "添加角色成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "添加角色失败");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_EDIT')")
    @PutMapping(value = "/edit")
    public Result editRole(@RequestBody Role role, int[] permissionIds) {
        try {
            roleService.editRole(role, permissionIds);
            return new Result(true, "修改角色成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "修改角色失败");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_DELETE')")
    @DeleteMapping("/delete")
    public Result deleteRole(int id){
        try {
            roleService.deleteRole(id);
            return new Result(true, "删除成功" );
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result(false, "删除失败" );
    }

}
