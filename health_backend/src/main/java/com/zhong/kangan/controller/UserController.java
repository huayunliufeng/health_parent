package com.zhong.kangan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhong.kangan.common.pojo.Role;
import com.zhong.kangan.common.pojo.User;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;
import com.zhong.kangan.common.result.Result;
import com.zhong.kangan.service.RoleService;
import com.zhong.kangan.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @author 华韵流风
 * @ClassName RoleController
 * @Date 2021/8/21 9:54
 * @packageName com.zhong.kangan.controller
 * @Description TODO
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @Reference
    private RoleService roleService;

    @PreAuthorize("hasAnyAuthority('USER_QUERY')")
    @PostMapping("/page")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

        try {
            return userService.findUserByKey(queryPageBean);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("分页查询失败！");
        }
    }

    @PreAuthorize("hasAnyAuthority('USER_QUERY','ROLE_QUERY')")
    @PostMapping("/rolePage")
    public PageResult findRolePage(@RequestBody QueryPageBean queryPageBean){
        try {
            return roleService.findRoleByKey(queryPageBean);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("分页查询失败！");
        }

    }

    @PreAuthorize("hasAnyAuthority('USER_QUERY','USER_EDIT')")
    @GetMapping("/update")
    public Result getUserById(@RequestParam("id") int id) {
        Object[] objects = new Object[2];
        try {
            Object res1 = getUser(id);
            Object res2 = getRoleIds(id);
            objects[0] = res1;
            objects[1] = res2;
            return new Result(true, "", objects);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "查询失败");
    }

    private Object getUser(int id) {
        return userService.findUserById(id);
    }

    private Object getRoleIds(int id) {
        return userService.findRoleIds(id);
    }

    @PreAuthorize("hasAnyAuthority('USER_ADD')")
    @PostMapping(value = "/add")
    public Result addUser(@RequestBody User user, int[] roleIds) {
        try {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userService.addUser(user, roleIds);
            return new Result(true, "添加角色成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "添加角色失败");
    }

    @PreAuthorize("hasAnyAuthority('USER_EDIT')")
    @PutMapping(value = "/edit")
    public Result editUser(@RequestBody User user, int[] roleIds) {
        try {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userService.editUser(user, roleIds);
            return new Result(true, "修改角色成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "修改角色失败");
    }

    @PreAuthorize("hasAnyAuthority('USER_DELETE')")
    @DeleteMapping("/delete")
    public Result deleteUser(int id){
        try {
            userService.deleteUser(id);
            return new Result(true, "删除成功" );
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result(false, "删除失败" );
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getUserName")
    public String getUserName() {
        //得到当前用户的对象
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

}
