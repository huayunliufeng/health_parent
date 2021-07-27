package com.zhong.kangan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhong.kangan.common.constant.MessageConstant;
import com.zhong.kangan.common.result.Result;
import com.zhong.kangan.service.CheckGroupCheckItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 华韵流风
 * @ClassName CheckGroupController
 * @Date 2021/7/27 16:26
 * @packageName com.zhong.kangan.controller
 * @Description TODO
 */
@RestController //把@ResponseBody 和@Controller合二为一
@RequestMapping("/checkgroupcheckitem") //窄化url
public class CheckGroupController {

    @Reference
    private CheckGroupCheckItemService cgciService;

    @GetMapping("/count")
    public Result count(@RequestParam("id") int id){
        try {
            int count = cgciService.findCountByCheckItemId(id);
            return new Result(true, "",count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "");
    }



}
