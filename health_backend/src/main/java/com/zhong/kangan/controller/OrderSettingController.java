package com.zhong.kangan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhong.kangan.common.constant.MessageConstant;
import com.zhong.kangan.common.pojo.OrderSetting;
import com.zhong.kangan.common.result.Result;
import com.zhong.kangan.common.utils.POIUtils;
import com.zhong.kangan.service.OrderSettingService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 华韵流风
 * @ClassName OrderSettingController
 * @Date 2021/8/10 17:10
 * @packageName com.zhong.kangan.controller
 * @Description TODO
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    @PostMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) {

        //利用POI工具读取文件数据
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            List<OrderSetting> orderSettings = new ArrayList<>();
            for (String[] os : list) {
                Date orderDate = new SimpleDateFormat("yyyy/MM/dd").parse(os[0]);
                int count = orderSettingService.findOrderSettingByDate(orderDate);
                OrderSetting orderSetting = new OrderSetting(orderDate, Integer.parseInt(os[1]));
                if (count != 0) {
                    orderSettingService.updateByOrderDate(orderSetting);
                } else {
                    orderSetting.setReservations(0);
                    orderSettings.add(orderSetting);
                }
            }
            orderSettingService.add(orderSettings);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
    }

    @GetMapping("/findOrderSettingByMonth")
    public Result findOrderSettingByMonth(@RequestParam("year") String year, @RequestParam("month") String month) {
        try{
            List<Map<String,Integer>> list = new ArrayList<>();
            List<OrderSetting> orderSettings = orderSettingService.findOrderSettingByMonth(year, month);
            Calendar cal = Calendar.getInstance();
            for (OrderSetting setting : orderSettings) {
                Map<String, Integer> map = new HashMap<>(3);
                cal.setTime(setting.getOrderDate());
                map.put("date", cal.get(Calendar.DAY_OF_MONTH));
                map.put("number", setting.getNumber());
                map.put("reservations", setting.getReservations());
                list.add(map);
            }
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
    }

    @PutMapping("/edit")
    public Result editOrderSetting(@RequestParam("day") String day,@RequestParam("number")String number){

        try{
            List<OrderSetting> orderSettings = new ArrayList<>();
            Date orderDate = new SimpleDateFormat("yyyy-MM-dd").parse(day);
            OrderSetting orderSetting = new OrderSetting(orderDate,Integer.parseInt(number));
            orderSettingService.updateByOrderDate(orderSetting);
            int count = orderSettingService.findOrderSettingByDate(orderDate);
            if (count != 0) {
                orderSettingService.updateByOrderDate(orderSetting);
            } else {
                orderSettings.add(orderSetting);
                orderSettingService.add(orderSettings);
            }
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.ORDERSETTING_FAIL);
    }



}
