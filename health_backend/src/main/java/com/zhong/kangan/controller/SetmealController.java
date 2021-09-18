package com.zhong.kangan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhong.kangan.common.constant.MessageConstant;
import com.zhong.kangan.common.constant.RedisConstant;
import com.zhong.kangan.common.pojo.Setmeal;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;
import com.zhong.kangan.common.result.Result;
import com.zhong.kangan.common.utils.FileUploadUtil;
import com.zhong.kangan.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * @author 华韵流风
 * @ClassName SetmealController
 * @Date 2021/7/27 16:26
 * @packageName com.zhong.kangan.controller
 * @Description TODO
 */
@RestController //把@ResponseBody 和@Controller合二为一
@RequestMapping("/setmeal") //窄化url
public class SetmealController {

    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;

    @PreAuthorize("hasAnyAuthority('SETMEAL_ADD')")
    @PostMapping(value = "/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        String fileName = imgFile.getOriginalFilename();
        int lastIndex = fileName.lastIndexOf(".");
        String suffix = fileName.substring(lastIndex);
        String uploadFileName = UUID.randomUUID().toString().replaceAll("-", "").trim() + suffix;

        byte[] uploadBytes;
        try {
            uploadBytes = imgFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }

        boolean result = FileUploadUtil.uploadFile(uploadBytes, uploadFileName);
        if (result) {
            //所有上传的文件添加到all集合
            Jedis jedis = jedisPool.getResource();
            jedis.sadd(RedisConstant.SETMEALPICALL,uploadFileName);
            //归还连接
            jedis.close();
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, uploadFileName);
        }
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }

    @PreAuthorize("hasAnyAuthority('SETMEAL_QUERY')")
    @PostMapping(value = "/page")
    public PageResult page(@RequestBody QueryPageBean pageBean) {

        try {
            return setmealService.findAllSetmealByPages(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("分页查询失败！");
        }
    }

    @PreAuthorize("hasAnyAuthority('SETMEAL_ADD')")
    @PostMapping(value = "/add")
    public Result addCheckGroup(@RequestBody Setmeal setmeal, int[] checkgroupIds) {
        try {
            setmealService.addSetmeal(setmeal, checkgroupIds);

            /*Jedis jedis = jedisPool.getResource();
            jedis.sadd(RedisConstant.SETMEALPICVALID, setmeal.getImg());
            jedis.close();*/
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
    }

    /*@GetMapping(value = "/cancel")
    public Result cancelUpload(String fileName) {
        if (deleteFile(fileName)) {
            return new Result(true, "取消成功！");
        } else {
            return new Result(false, "文件不存在！");
        }

    }*/

    @PreAuthorize("hasAnyAuthority('SETMEAL_EDIT','SETMEAL_QUERY')")
    @GetMapping(value = "/update")
    public Result getInfo(@RequestParam("id") int id) {
        Object[] objects = new Object[2];
        try {
            Object res1 = getSetmeal(id);
            Object res2 = getSetmealCheckGroup(id);
            objects[0] = res1;
            objects[1] = res2;
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, objects);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
    }

    private Object getSetmeal(int id) {
        return setmealService.findSetmeal(id);
    }

    private Object getSetmealCheckGroup(int id) {
        return setmealService.findSetmealCheckGroup(id);
    }

    @PreAuthorize("hasAnyAuthority('SETMEAL_EDIT')")
    @PutMapping(value = "/edit")
    public Result editCheckGroup(@RequestBody Setmeal setmeal, int[] checkgroupIds) {
        try {
            setmealService.editSetmeal(setmeal, checkgroupIds);
            /*Jedis jedis = jedisPool.getResource();
            jedis.sadd(RedisConstant.SETMEALPICVALID, setmeal.getImg());
            jedis.close();*/
            return new Result(true, "修改套餐数据成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "修改套餐数据失败");
    }

    @PreAuthorize("hasAnyAuthority('SETMEAL_DELETE')")
    @DeleteMapping(value = "/delete")
    public Result delete(@RequestParam int id, String img) {
        try {
            setmealService.deleteSetmeal(id);
            /*Jedis jedis = jedisPool.getResource();
            jedis.srem(RedisConstant.SETMEALPICVALID, img);
            jedis.close();*/
            return new Result(true, "删除套餐成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
    }


    /**
     * 删除文件
     *
     * @param fileName fileName
     * @return boolean
     */
    public boolean deleteFile(String fileName) {
        return FileUploadUtil.deleteFile(fileName);
    }


}
