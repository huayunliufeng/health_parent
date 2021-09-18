package com.zhong.kangan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhong.kangan.common.pojo.Setmeal;
import com.zhong.kangan.common.result.Result;
import com.zhong.kangan.service.OrderService;
import com.zhong.kangan.service.ReportService;
import com.zhong.kangan.service.SetmealService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 华韵流风
 * @ClassName ReportController
 * @Date 2021/8/25 19:38
 * @packageName com.zhong.kangan.controller
 * @Description TODO
 */

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private ReportService reportService;

    @Reference
    private OrderService orderService;

    @Reference
    private SetmealService setmealService;

    @PreAuthorize("hasAnyAuthority('REPORT_VIEW')")
    @GetMapping("/getMemberReport")
    public Result getMemberReport(int year) {
        int[] months = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int[] memberCount = new int[12];
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("months", months);
            for (int i = 0; i < months.length; i++) {
                memberCount[i] = reportService.findMemberCountByYearAndMonth(year, months[i]);
            }
            map.put("memberCount", memberCount);
            return new Result(true, "查询成功", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "查询失败");
    }

    @PreAuthorize("hasAnyAuthority('REPORT_VIEW')")
    @GetMapping("/getSetmealReport")
    public Result getSetmealReport() {
        try {
            List<Map<String, Object>> list = new ArrayList<>();
            List<Setmeal> setmeals = orderService.findAllSetmeal();
            for (Setmeal setmeal : setmeals) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", setmeal.getName());
                map.put("value", reportService.findOrderCountBySetId(setmeal.getId()));
                list.add(map);
            }
            return new Result(true, "查询成功", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "查询失败");
    }

    @PreAuthorize("hasAnyAuthority('REPORT_VIEW')")
    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            return new Result(true, "查询成功", getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "查询失败");
    }

    @PreAuthorize("hasAnyAuthority('REPORT_VIEW')")
    @GetMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {

        try {
            Map<String, Object> result = getResult();
            //取出返回结果数据，准备将报表数据写入到Excel文件中
            //日期
            String reportDate = (String) result.get("reportDate");
            //新增会员数
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            //会员总数
            Integer totalMember = (Integer) result.get("totalMember");
            //本周新增会员
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            //本月新增会员
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            //今日预约
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            //本周预约
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            //本月预约
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            //今日到诊
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            //本周到诊
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            //本月到诊
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            //热门套餐
            List<Map<String, Object>> hotSetmeal = (List<Map<String, Object>>) result.get("hotSetmeal");
            //获取模板文件在项目部署中的虚拟路径  File.separator 分隔符 根据系统来决定是/还是\。
            String filePath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
            //基于模板在内存中创建一个excel表格对象
            XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File(filePath)));
            //读取第一个工作表
            XSSFSheet sheet = excel.getSheetAt(0);

            //第三行
            XSSFRow row = sheet.getRow(2);
            //日期
            row.getCell(5).setCellValue(reportDate);

            //会员数据统计
            //第五行
            row = sheet.getRow(4);
            //新增会员数
            row.getCell(5).setCellValue(todayNewMember);
            //总会员数
            row.getCell(7).setCellValue(totalMember);
            //第六行
            row = sheet.getRow(5);
            //本周新增会员
            row.getCell(5).setCellValue(thisWeekNewMember);
            //本月新增会员
            row.getCell(7).setCellValue(thisMonthNewMember);

            //预约到诊数据统计
            //第八行
            row = sheet.getRow(7);
            //今日预约
            row.getCell(5).setCellValue(todayOrderNumber);
            //今日到诊
            row.getCell(7).setCellValue(todayVisitsNumber);
            //第九行
            row = sheet.getRow(8);
            //本周预约
            row.getCell(5).setCellValue(thisWeekOrderNumber);
            //本周到诊
            row.getCell(7).setCellValue(thisWeekVisitsNumber);
            //第十行
            row = sheet.getRow(9);
            //本月预约
            row.getCell(5).setCellValue(thisMonthOrderNumber);
            //本月到诊
            row.getCell(7).setCellValue(thisMonthVisitsNumber);

            //热门套餐
            int rowNum = 12;
            for (Map<String, Object> setmeal : hotSetmeal) {
                //第 rowNum + 1 行
                row = sheet.getRow(rowNum++);
                //套餐名称
                row.getCell(4).setCellValue((String) setmeal.get("name"));
                //预约数量
                row.getCell(5).setCellValue((Integer) setmeal.get("setmeal_count"));
                //占比
                row.getCell(6).setCellValue((String) setmeal.get("proportion"));
                //套餐说明
                row.getCell(7).setCellValue((String) setmeal.get("remark"));
            }

            //使用输出流进行表格下载，基于浏览器作为客户端下载
            OutputStream out = response.getOutputStream();
            //告诉浏览器是什么类型的文件 代表的是Excel文件类型
            response.setContentType("application/vnd.ms-excel");
            //设置响应头信息 指定以附件形式进行下载
            response.setHeader("content-Disposition", "attachment;filename=report_" + reportDate + ".xlsx");
            excel.write(out);
            out.flush();
            out.close();
            excel.close();
            return new Result(true, "准备下载");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "导出错误");

    }


    private Map<String, Object> getResult() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        map.put("reportDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        map.put("todayNewMember", reportService.getTodayNewMember());
        map.put("totalMember", reportService.getTotalMember());
        map.put("thisWeekNewMember", reportService.getThisWeekNewMember());
        map.put("thisMonthNewMember", reportService.getThisMonthNewMember());
        map.put("todayOrderNumber", reportService.getTodayOrderNumber());
        map.put("todayVisitsNumber", reportService.getTodayVisitsNumber());
        map.put("thisWeekOrderNumber", reportService.getThisWeekOrderNumber());
        map.put("thisWeekVisitsNumber", reportService.getThisWeekVisitsNumber());
        map.put("thisMonthOrderNumber", reportService.getThisMonthOrderNumber());
        map.put("thisMonthVisitsNumber", reportService.getThisMonthVisitsNumber());

        int[] ids = reportService.getHotSetmealIds();
        int totalCount = reportService.findAllCountOrder();
        for (int id : ids) {
            Map<String, Object> tmp = new HashMap<>();
            Setmeal setmeal = setmealService.findSetmeal(id);
            int count = reportService.findOrderCountBySetId(setmeal.getId());
            tmp.put("name", setmeal.getName());
            tmp.put("setmeal_count", count);
            tmp.put("proportion", String.format("%.2f", count * 1.0 / totalCount));
            tmp.put("remark", setmeal.getRemark());
            list.add(tmp);
        }
        map.put("hotSetmeal", list);
        return map;
    }

}
