package com.liujiajun.controller;

import com.github.pagehelper.PageInfo;
import com.liujiajun.domain.SysLog;
import com.liujiajun.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    private ISysLogService sysLogService;

    //查找所有
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(value = "page",required = true,defaultValue = "1")Integer page,
                                @RequestParam(value = "pageSize",required = true,defaultValue = "4")Integer pageSize) throws Exception {
        ModelAndView mv=new ModelAndView();

        List<SysLog> sysLogs=sysLogService.findAll(page,pageSize);
        PageInfo pageInfo=new PageInfo(sysLogs);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("syslog-list");

        return mv;
    }


    //批量删除
    @RequestMapping("/delete.do")
    public String delete(String[] selectIds) throws Exception {

        sysLogService.delete(selectIds);

        return "redirect:findAll.do";
    }


    //搜索日志
    @RequestMapping("/findSysLog.do")
    public ModelAndView findSysLog(SysLog sysLog,
                                   @RequestParam(value = "page",required = true,defaultValue = "1")Integer page,
                                   @RequestParam(value = "pageSize",required = true,defaultValue = "4")Integer pageSize) throws Exception {

        ModelAndView mv=new ModelAndView();

        List<SysLog> findSysLog=sysLogService.findByUsername(sysLog.getUsername(),page,pageSize);
        PageInfo pageInfo=new PageInfo(findSysLog);

        mv.addObject("findUsername",sysLog.getUsername());
        mv.addObject("pageInfo",pageInfo);

        mv.setViewName("syslog-find-list");

        return mv;

    }

}
