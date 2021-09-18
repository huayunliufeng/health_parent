package com.zhong.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 华韵流风
 * @ClassName SecurityController
 * @Date 2021/8/20 15:46
 * @packageName com.zhong.security.controller
 * @Description TODO
 */
@RestController
@RequestMapping("/security")
public class SecurityController {


    @PreAuthorize("hasAnyAuthority('add')")
    @RequestMapping("/add")
    public String add() {
        return "add success";
    }

    @PreAuthorize("hasAnyAuthority('delete')")
    @RequestMapping("/delete")
    public String delete() {
        return "delete success";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/find")
    public String find() {
        //得到当前用户的对象
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "username=" + user.getUsername();
    }
}
