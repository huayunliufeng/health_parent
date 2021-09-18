package com.zhong.kangan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhong.kangan.common.constant.MessageConstant;
import com.zhong.kangan.common.pojo.Member;
import com.zhong.kangan.common.pojo.Order;
import com.zhong.kangan.common.pojo.OrderSetting;
import com.zhong.kangan.common.pojo.Setmeal;
import com.zhong.kangan.common.result.Result;
import com.zhong.kangan.common.utils.TXSMSUtils;
import com.zhong.kangan.common.utils.ValidateCodeUtils;
import com.zhong.kangan.service.MemberService;
import com.zhong.kangan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author 华韵流风
 * @ClassName OrderController
 * @Date 2021/8/13 20:33
 * @packageName com.zhong.kangan.controller
 * @Description TODO
 */
@RestController
@RequestMapping("/setmeal")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("getSetmeal")
    public Result getSetmeal() {
        try {
            List<Setmeal> setmeals = orderService.findAllSetmeal();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeals);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
    }

    @PostMapping("findById")
    public Result findSetmealById(@RequestParam("id") int id) {
        try {
            Setmeal setmeal = orderService.findSetmealById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
    }

    @GetMapping("/sendCode")
    public Result sendSMSCode(String phoneNumber) {
        try {
            if (phoneNumber == null || "".equals(phoneNumber.trim())) {
                return new Result(false, "手机号不能为空！");
            }
            String pattern = "1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}";
            boolean isMatch = Pattern.matches(pattern, phoneNumber);
            if (!isMatch) {
                return new Result(false, "手机号格式错误！");
            }

            String code = String.valueOf(ValidateCodeUtils.generateValidateCode(6));
            boolean res = TXSMSUtils.sendShortMessage("+86" + phoneNumber, code);
            if (res) {
                Jedis jedis = jedisPool.getResource();
                jedis.set(phoneNumber, code);
                jedis.expire(phoneNumber, 60);
                jedis.close();
                return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
    }

    @PostMapping("/subOrder")
    public Result submitOrder(@RequestBody Member member, String vailCode, int setmealId, String orderDate) {

        try {
            //验证电话号码和验证码是否填写
            if (member.getPhoneNumber() == null || "".equals(member.getPhoneNumber().trim()) || "".equals(vailCode.trim()) || "null".equals(vailCode)) {
                return new Result(false, MessageConstant.TELEPHONE_VALIDATECODE_NOTNULL);
            }

            //验证姓名和身份证号是否填写
            if (member.getName() == null || "".equals(member.getName().trim()) || member.getIdCard() == null || "".equals(member.getIdCard().trim())) {
                return new Result(false, "请填写姓名和身份证号");
            }

            //验证身份证号
            String pattern = "[1-9]\\d{5}(18|19|20|(3\\d))\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]";
            boolean isMatch = Pattern.matches(pattern, member.getIdCard());
            if (!isMatch) {
                return new Result(false, "身份证格式错误");
            }

            //验证身份证号和性别是否匹配
            int tmp = Integer.parseInt(member.getIdCard().substring(16, 17));
            tmp = tmp % 2 == 0 ? 2 : 1;

            if (tmp != Integer.parseInt(member.getSex())) {
                return new Result(false, "所选性别与身份证号不符！");
            }

            //验证日期是否合法
            if ("null".equals(orderDate) || "".equals(orderDate.trim())) {
                return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
            }
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

            //验证码是否正确
            Jedis jedis = jedisPool.getResource();
            String code = jedis.get(member.getPhoneNumber());
            jedis.close();
            if (!code.equals(vailCode)) {
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }

            member.setRegTime(new Date());
            Member member1 = memberService.findMemberByPhoneNumber(member.getPhoneNumber());
            if (member1 != null) {
                //验证是否已经预约
                List<Order> orders = orderService.getOrderByMemId(member1.getId());
                for (Order order : orders) {
                    if (order.getSetmealId() == setmealId) {
                        return new Result(false, MessageConstant.HAS_ORDERED);
                    }
                }
                Order order = new Order(member1.getId(), orderDateX, Order.ORDERTYPE_WEIXIN, Order.ORDERSTATUS_NO, setmealId);
                int id = orderService.addOrder(order);
                return new Result(true, MessageConstant.ORDER_SUCCESS, id);

            } else {
                int memberId = memberService.addMember(member);

                //验证预约是否已满
                if (orderSetting.getNumber() <= orderSetting.getReservations()) {
                    return new Result(false, MessageConstant.ORDER_FULL);
                } else {
                    Order order = new Order(memberId, orderDateX, Order.ORDERTYPE_WEIXIN, Order.ORDERSTATUS_NO, setmealId);
                    int id = orderService.addOrder(order);
                    return new Result(true, MessageConstant.ORDER_SUCCESS, id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "预约失败！");
    }

    @GetMapping("/findOrderById")
    public Result findOrderById(int id) {
        try {
            Map<String, String> map = new HashMap<>();
            Order order = orderService.getOrderById(id);
            Setmeal setmeal = orderService.findSetmealById(order.getSetmealId());
            Member member = memberService.findMemberById(order.getMemberId());
            map.put("name", member.getName());
            map.put("setmeal", setmeal.getName());
            map.put("orderDate", new SimpleDateFormat("yyyy-MM-dd").format(order.getOrderDate()));
            map.put("orderType", order.getOrderType());
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(true, MessageConstant.QUERY_ORDER_FAIL);
    }

    @GetMapping("/getInfo")
    public Result getMemberInfo(HttpServletRequest request){
        try {
            Cookie[] cookies = request.getCookies();
            String phoneNumber = null;
            if(cookies != null && cookies.length > 0){
                for (Cookie cookie : cookies){
                    if("phoneNumber".equals(cookie.getName())){
                        phoneNumber = cookie.getValue();
                        break;
                    }
                }
            }
            Jedis jedis = jedisPool.getResource();
            jedis.select(1);
            String memberStr = jedis.get(phoneNumber);
            Member member = JSON.toJavaObject(JSONObject.parseObject(memberStr), Member.class);
            return new Result(true, "",member);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result(false, "");

    }


    private String getCode(int length) {
        int[] tmp = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buff.append(tmp[new Random().nextInt(10)]);
        }
        return buff.toString();
    }


}
