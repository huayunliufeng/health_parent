package com.zhong.kangan.service;

import com.zhong.kangan.common.pojo.Member;
import com.zhong.kangan.common.pojo.Setmeal;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @author 华韵流风
 * @ClassName MemberService
 * @Date 2021/8/16 17:47
 * @packageName com.zhong.kangan.service
 * @Description TODO
 */
public interface MemberService {

    /**
     * 根据手机号查询Member
     *
     * @param phoneNumber phoneNumber
     * @return Member
     */
    Member findMemberByPhoneNumber(String phoneNumber);

    /**
     * 添加会员
     *
     * @param member member
     * @return int
     */
    int addMember(Member member);

    /**
     * 根据id查询member
     *
     * @param id id
     * @return Member
     */
    Member findMemberById(int id);

    /**
     * 根据关键字查询Member
     *
     * @param queryPageBean queryPageBean
     * @return PageResult
     */
    PageResult findMemberPage(QueryPageBean queryPageBean);

    /**
     * 修改订单状态
     *
     * @param id id
     */
    void edit(int id);

    /**
     * 删除订单
     *
     * @param id id
     */
    void delete(int id);

    /**
     * 获取所有套餐
     *
     * @return List<Setmeal>
     */
    List<Setmeal> getAllSetmeal();


}
