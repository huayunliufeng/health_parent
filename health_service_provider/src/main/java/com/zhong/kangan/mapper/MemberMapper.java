package com.zhong.kangan.mapper;

import com.zhong.kangan.common.pojo.Member;
import com.zhong.kangan.common.pojo.Setmeal;

import java.util.List;

/**
 * @author 华韵流风
 * @ClassName MemberServiceImpl
 * @Date 2021/8/16 18:00
 * @packageName com.zhong.kangan.mapper
 * @Description TODO
 */
public interface MemberMapper {

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
     */
    void addMember(Member member);

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
     * @param key key
     * @return List<Member>
     */
    List<Member> findMemberByKey(String key);

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

    /**
     * 根据id和key查询
     *
     * @param id  id
     * @param key key
     * @return List<Member>
     */
    List<Member> findMemByIdAndKey(int id, String key);
}
