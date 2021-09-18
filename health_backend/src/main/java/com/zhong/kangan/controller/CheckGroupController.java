package com.zhong.kangan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhong.kangan.common.constant.MessageConstant;
import com.zhong.kangan.common.pojo.CheckGroup;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;
import com.zhong.kangan.common.result.Result;
import com.zhong.kangan.service.CheckGroupCheckItemService;
import com.zhong.kangan.service.CheckGroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 华韵流风
 * @ClassName CheckGroupController
 * @Date 2021/7/27 16:26
 * @packageName com.zhong.kangan.controller
 * @Description TODO
 */
@RestController //把@ResponseBody 和@Controller合二为一
@RequestMapping("/checkgroup") //窄化url
public class CheckGroupController {

    @Reference
    private CheckGroupCheckItemService cgciService;

    @Reference
    private CheckGroupService checkGroupService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/count")
    public Result count(@RequestParam("id") int id) {
        try {
            int count = cgciService.findCountByCheckItemId(id);
            return new Result(true, "", count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "");
    }

    @PreAuthorize("hasAnyAuthority('CHECKGROUP_QUERY')")
    @PostMapping(value = "/page")
    public PageResult page(@RequestBody QueryPageBean pageBean) {

        try {
            return checkGroupService.findAllCheckGroupByPages(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("分页查询失败！");
        }
    }

    @PreAuthorize("hasAnyAuthority('CHECKGROUP_DELETE')")
    @DeleteMapping(value = "/delete")
    public Result delete(@RequestParam int id) {
        try {
            checkGroupService.delete(id);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
    }

    @PreAuthorize("hasAnyAuthority('CHECKGROUP_ADD')")
    @PostMapping(value = "/add")
    public Result addCheckGroup(@RequestBody CheckGroup checkGroup, int[] checkitemIds) {
        try {
            checkGroupService.addCheckGroup(checkGroup, checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
    }

    @PreAuthorize("hasAnyAuthority('CHECKGROUP_EDIT','CHECKGROUP_QUERY')")
    @GetMapping(value = "/update")
    public Result getInfo(@RequestParam("id") int id) {
        Object[] objects = new Object[2];
        try {
            Object res1 = getCheckGroup(id);
            Object res2 = getCheckGroupCheckItem(id);
            objects[0] = res1;
            objects[1] = res2;
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, objects);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
    }

    private Object getCheckGroup(int id) {
        return checkGroupService.findCheckGroupById(id);
    }

    private Object getCheckGroupCheckItem(int id) {
        return checkGroupService.findCheckGroupCheckItemById(id);
    }

    @PreAuthorize("hasAnyAuthority('CHECKGROUP_EDIT')")
    @PutMapping(value = "/edit")
    public Result editCheckGroup(@RequestBody CheckGroup checkGroup, int[] checkitemIds) {
        try {
            checkGroupService.editCheckGroup(checkGroup, checkitemIds);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
    }

}
