package com.zhong.kangan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhong.kangan.common.pojo.Member;
import com.zhong.kangan.common.pojo.Order;
import com.zhong.kangan.common.pojo.Setmeal;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;
import com.zhong.kangan.mapper.MemberMapper;
import com.zhong.kangan.mapper.OrderMapper;
import com.zhong.kangan.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 华韵流风
 * @ClassName MemberServiceImpl
 * @Date 2021/8/16 18:00
 * @packageName com.zhong.kangan.mapper
 * @Description TODO
 */
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Member findMemberByPhoneNumber(String phoneNumber) {
        return memberMapper.findMemberByPhoneNumber(phoneNumber);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addMember(Member member) {
        memberMapper.addMember(member);
        return member.getId();
    }

    @Override
    public Member findMemberById(int id) {
        return memberMapper.findMemberById(id);
    }

    @Override
    public List<Setmeal> getAllSetmeal() {
        return memberMapper.getAllSetmeal();
    }

    @Override
    public PageResult findMemberPage(QueryPageBean queryPageBean) {
        List<Map<String, String>> list = new ArrayList<>();
        List<Member> members = memberMapper.findMemberByKey(queryPageBean.getQueryString());
//        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        for (Member member : members) {
            List<Order> orders = orderMapper.getOrderByMemId(member.getId());
            for (Order order : orders) {
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(order.getId()));
                map.put("orderDate", new SimpleDateFormat("yyyy年MM月dd日").format(order.getOrderDate()));
                map.put("fileNumber", member.getFileNumber());
                map.put("name", member.getName());
                map.put("phoneNumber", member.getPhoneNumber());
                map.put("orderType", order.getOrderType());
                map.put("orderStatus", order.getOrderStatus());
                map.put("setmeal_id", String.valueOf(order.getSetmealId()));
                list.add(map);
            }
        }

        //手动分页
        List<Map<String, String>> tmp = new ArrayList<>();
        int begin = (queryPageBean.getCurrentPage() - 1) * queryPageBean.getPageSize();
        int end = Math.min(begin + queryPageBean.getPageSize(), list.size());
        for (int i = begin; i < end; i++) {
            tmp.add(list.get(i));
        }

//        PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String, String>>(list);
        return new PageResult((long) list.size(), tmp);
        /*Page<Map<String,String>> page = (Page<Map<String, String>>) list;
        return new PageResult(page.getTotal(),page.getResult());*/
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(int id) {
        memberMapper.edit(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(int id) {
        memberMapper.delete(id);
    }
}
