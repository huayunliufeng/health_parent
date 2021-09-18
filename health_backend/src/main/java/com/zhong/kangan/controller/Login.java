package com.zhong.kangan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhong.kangan.common.pojo.Permission;
import com.zhong.kangan.common.pojo.Role;
import com.zhong.kangan.common.pojo.User;
import com.zhong.kangan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 华韵流风
 * @ClassName LoginController
 * @Date 2021/8/21 16:35
 * @packageName com.zhong.kangan.controller
 * @Description TODO
 */
@Controller
public class Login implements UserDetailsService {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(s);
        if (user == null) {
            return null;
        }
        List<SimpleGrantedAuthority> list = new ArrayList();
        for (Role role : user.getRoles()) {
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            for (Permission permission : role.getPermissions()) {
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), list);
    }

}
