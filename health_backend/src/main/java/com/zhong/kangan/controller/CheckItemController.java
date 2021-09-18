package com.zhong.kangan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhong.kangan.common.constant.MessageConstant;
import com.zhong.kangan.common.pojo.CheckItem;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;
import com.zhong.kangan.common.result.Result;
import com.zhong.kangan.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 华韵流风
 * @ClassName CheckItemController
 * @Description TODO
 * @Date 2021/7/24 16:12
 * @packageName com.zhong.kangan.controller
 */


@RestController //把@ResponseBody 和@Controller合二为一
@RequestMapping("/checkitem") //窄化url
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    @PreAuthorize("hasAnyAuthority('CHECKITEM_ADD')")
    @PostMapping(value = "/add")
    public Result addCheckItem(@RequestBody CheckItem checkItem) {

        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
    }

    @PreAuthorize("hasAnyAuthority('CHECKITEM_QUERY')")
    @PostMapping(value = "/page")
    public PageResult page(@RequestBody QueryPageBean queryPageBean) {

        try {
            return checkItemService.findCheckItemsByPageByCriteria(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("分页查询失败！");
        }

    }

    @PreAuthorize("hasAnyAuthority('CHECKITEM_EDIT','CHECKITEM_EDIT')")
    @GetMapping(value = "/update")
    public Result getById(@RequestParam("id") int id) {

        try {
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
    }

    @PreAuthorize("hasAnyAuthority('CHECKITEM_EDIT')")
    @PutMapping(value = "/edit")
    public Result edit(@RequestBody CheckItem checkItem) {

        try {
            checkItemService.edit(checkItem);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
    }

    @PreAuthorize("hasAnyAuthority('CHECKITEM_DELETE')")
    @DeleteMapping(value = "/delete")
    public Result delete(@RequestParam int id) {

        try {
            checkItemService.delete(id);
            return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
    }





}
