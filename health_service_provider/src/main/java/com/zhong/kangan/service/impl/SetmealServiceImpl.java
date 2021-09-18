package com.zhong.kangan.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhong.kangan.common.pojo.Setmeal;
import com.zhong.kangan.common.querybean.QueryPageBean;
import com.zhong.kangan.common.result.PageResult;
import com.zhong.kangan.mapper.OrderMapper;
import com.zhong.kangan.mapper.SetmealMapper;
import com.zhong.kangan.service.SetmealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 华韵流风
 * @ClassName SetmealServiceImpl
 * @Date 2021/7/30 18:14
 * @packageName com.zhong.kangan.service.impl
 * @Description TODO
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${static_out_path}")
    private String static_out_path;
    @Value("${setmeal_html_name}")
    private String setmeal_html_name;
    @Value("${setmealdetail_html_name}")
    private String setmealdetail_html_name;

    @Override
    public PageResult findAllSetmealByPages(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Setmeal> setmeal = (Page<Setmeal>) setmealMapper.findAllSetmealByPages(queryPageBean.getQueryString());
        return new PageResult(setmeal.getTotal(), setmeal.getResult());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSetmeal(Setmeal setmeal, int[] checkgroupIds) {
        setmealMapper.addSetmeal(setmeal);
        if (checkgroupIds.length != 0) {
            setmealMapper.addSetmealCheckGroup(setmeal.getId(), checkgroupIds);
        }
        //生成套餐列表静态页面
        generalSetmealList();
        //生成套餐详情静态页面
        generalSetmealDetail(setmeal.getId());
    }

    private void generalSetmealDetail(int id) {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("setmeal", orderMapper.findSetmealById(id));
        generalHtml(dataModel, "setmeal_detail.ftl", setmealdetail_html_name + id + ".html");
    }

    private void generalSetmealList() {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("setmealList", orderMapper.findAllSetmeal());
        generalHtml(dataModel, "setmeal.ftl", setmeal_html_name);
    }

    /**
     * 通用方法，只要生成静态页面都要调用
     */
    private void generalHtml(Map<String, Object> dataModel, String templateName, String targetHtml) {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer out = null;
        try {
            //本异常与前台的用户没有任何关系，可以try
            Template template = configuration.getTemplate(templateName);
            //输出流，把生成的静态文件输出到磁盘上
            out = new FileWriter(static_out_path + targetHtml);
            //通过模板文件
            template.process(dataModel, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public Setmeal findSetmeal(int id) {
        return setmealMapper.findSetmeal(id);
    }

    @Override
    public int[] findSetmealCheckGroup(int id) {
        return setmealMapper.findSetmealCheckGroup(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editSetmeal(Setmeal setmeal, int[] checkgroupIds) {
        setmealMapper.editSetmeal(setmeal);
        setmealMapper.deleteSetmealCheckGroup(setmeal.getId());
        if (checkgroupIds.length != 0) {
            setmealMapper.addSetmealCheckGroup(setmeal.getId(), checkgroupIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSetmeal(int id) {
        setmealMapper.deleteSetmealCheckGroup(id);
        setmealMapper.deleteSetmeal(id);
    }

}
