package com.zhong.kangan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhong.kangan.common.constant.MessageConstant;
import com.zhong.kangan.common.pojo.Member;
import com.zhong.kangan.common.pojo.Order;
import com.zhong.kangan.common.pojo.OrderSetting;
import com.zhong.kangan.common.pojo.Setmeal;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;
import com.zhong.kangan.common.result.Result;
import com.zhong.kangan.service.MemberService;
import com.zhong.kangan.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 华韵流风
 * @ClassName MapperController
 * @Date 2021/8/16 19:00
 * @packageName com.zhong.kangan.controller
 * @Description TODO
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Reference
    private MemberService memberService;

    @Reference
    private OrderService orderService;

    @PostMapping(value = "/page")
    public PageResult page(@RequestBody QueryPageBean queryPageBean) {

        try {
            return memberService.findMemberPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("分页查询失败！");
        }
    }

    @PutMapping(value = "/edit")
    public Result edit(@RequestParam("id") int[] ids) {
        int success = 0;
        int fail = 0;
        try {
            for (int id : ids) {
                Order order = orderService.getOrderById(id);
                if (!order.getOrderStatus().equals(Order.ORDERSTATUS_YES)) {
                    memberService.edit(id);
                    success++;
                }else{
                    fail++;
                }
            }
            return new Result(true, "成功：" + success + "，失败：" + fail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "成功：" + success + "，失败：" + fail);
    }

    @DeleteMapping(value = "/delete")
    public Result delete(@RequestParam("id") int[] ids) {
        int success = 0;
        int fail = 0;
        try {
            for (int id : ids) {
                Order order = orderService.getOrderById(id);
                if (order.getOrderStatus().equals(Order.ORDERSTATUS_YES)) {
                    fail++;
                } else {
                    memberService.delete(id);
                    success++;
                }
            }
            return new Result(true, "成功：" + success + "，失败：" + fail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "成功：" + success + "，失败：" + fail);
    }

    @GetMapping("/getAllSetmeal")
    public Result getAllSetmeal() {
        try {
            List<Setmeal> setmeals = memberService.getAllSetmeal();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeals);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(true, MessageConstant.QUERY_SETMEAL_FAIL);
    }

    @PostMapping(value = "/add")
    public Result addOrder(@RequestBody Member member, int setmeal_id, String orderDate) {
        try {
            Date orderDateX = new SimpleDateFormat("yyyy-MM-dd").parse(orderDate);
            //验证时间是否错误
            if (orderDateX.getTime() <= System.currentTimeMillis()) {
                return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
            }
            OrderSetting orderSetting = orderService.getResNum(orderDateX);
            //验证所选日期是否可以预约
            if (orderSetting == null) {
                return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
            }
            member.setRegTime(new Date());
            Member member1 = memberService.findMemberByPhoneNumber(member.getPhoneNumber());
            if (member1 != null) {
                //验证是否已经预约
                List<Order> orders = orderService.getOrderByMemId(member1.getId());
                for (Order order : orders) {
                    if (order.getSetmealId() == setmeal_id) {
                        return new Result(false, MessageConstant.HAS_ORDERED);
                    }
                }
                Order order = new Order(member1.getId(), orderDateX, Order.ORDERTYPE_TELEPHONE, Order.ORDERSTATUS_NO, setmeal_id);
                orderService.addOrder(order);
                return new Result(true, MessageConstant.ORDER_SUCCESS);

            } else {
                int memberId = memberService.addMember(member);

                //验证预约是否已满
                if (orderSetting.getNumber() <= orderSetting.getReservations()) {
                    return new Result(false, MessageConstant.ORDER_FULL);
                } else {
                    Order order = new Order(memberId, orderDateX, Order.ORDERTYPE_WEIXIN, Order.ORDERSTATUS_NO, setmeal_id);
                    orderService.addOrder(order);
                    return new Result(true, MessageConstant.ORDER_SUCCESS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.ORDER_FULL);

    }


}
