package com.liujiajun.controller;

import com.github.pagehelper.PageInfo;
import com.liujiajun.domain.Permission;
import com.liujiajun.domain.Role;
import com.liujiajun.service.IPermissionService;
import com.liujiajun.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                @RequestParam(value = "pageSize",defaultValue = "4")Integer pageSize) throws Exception {

        ModelAndView mv=new ModelAndView();
        List<Permission> roleList = permissionService.findAll(page,pageSize);

        PageInfo pageInfo=new PageInfo(roleList);

        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("permission-list");
        return mv;
    }

    //添加
    @RequestMapping("/save.do")
    public String save(Permission permission) throws Exception {
        permissionService.save(permission);

        return "redirect:findAll.do";
    }

    //删除
    @RequestMapping("delete.do")
    public String delete(String[] selectIds) throws Exception {

        permissionService.delete(selectIds);

        return "redirect:findAll.do";

    }

    //编辑
    @RequestMapping("edit.do")
    public ModelAndView edit(String id) throws Exception {

        ModelAndView mv=new ModelAndView();

        Permission permission = permissionService.findById(id);
        mv.addObject("permission",permission);
        mv.setViewName("permission-edit");

        return mv;
    }

    //修改
    @RequestMapping("update.do")
    public String update(Permission permission) throws Exception {

        permissionService.update(permission);
        return "redirect:findAll.do";
    }

}
