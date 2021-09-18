package com.zhong.kangan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhong.kangan.common.constant.MessageConstant;
import com.zhong.kangan.common.pojo.Member;
import com.zhong.kangan.common.result.Result;
import com.zhong.kangan.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 华韵流风
 * @ClassName LoginController
 * @Date 2021/8/17 16:34
 * @packageName com.zhong.kangan.controller
 * @Description TODO
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    @PostMapping("/check")
    public Result check(String telephone, String validateCode, HttpServletResponse response){

        try {
            //验证码是否正确
            Jedis jedis = jedisPool.getResource();
            String code = jedis.get(telephone);
            jedis.close();
            if (!code.equals(validateCode)) {
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }
            //将电话号码写入Cookie
            Cookie cookie = new Cookie("phoneNumber", telephone);
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
            Member member = memberService.findMemberByPhoneNumber(telephone);
            if(member==null){
                member = new Member();
                member.setPhoneNumber(telephone);
                memberService.addMember(member);
            }
            String mem = JSON.toJSON(member).toString();
            jedis = jedisPool.getResource();
            jedis.select(1);
            jedis.set(telephone, mem);
            jedis.close();
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result(false, "验证失败");
    }


}
