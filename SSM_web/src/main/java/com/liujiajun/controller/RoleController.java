package com.liujiajun.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liujiajun.dao.IRoleDao;
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
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                @RequestParam(value = "pageSize",defaultValue = "4")Integer pageSize) throws Exception {

        ModelAndView mv=new ModelAndView();
        List<Role> roleList = roleService.findAll(page,pageSize);

        PageInfo pageInfo=new PageInfo(roleList);

        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("role-list");
        return mv;
    }

    //添加角色前，查找所有资源权限
    @RequestMapping("/findAllPermission.do")
    public ModelAndView findAllPermission() throws Exception {

        ModelAndView mv=new ModelAndView();

        List<Permission> permissionList = permissionService.findAll(1, 30);
        mv.addObject("permissionList",permissionList);
        mv.setViewName("role-add");
        return mv;
    }

    //添加角色
    @RequestMapping("/save.do")
    public String save(Role role,String[] permissionIds) throws Exception {
        roleService.save(role);
        //因为id是随机生成的，所以要根据角色名称查找角色
        role=roleService.findByName(role.getRoleName());
        roleService.addPermission(role.getId(),permissionIds);
        return "redirect:findAll.do";
    }

    //删除角色
    @RequestMapping("delete.do")
    public String delete(String[] selectIds) throws Exception {

        roleService.delete(selectIds);

        return "redirect:findAll.do";

    }


    /**
     * 查看角色详情
     * @return
     */
    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) throws Exception {
        ModelAndView mv=new ModelAndView();
        Role role = roleService.findByRoleId(id);
        mv.addObject("role",role);
        mv.setViewName("role-show");
        return mv;
    }


    /**
     * 用户信息编辑
     * @return
     */
    @RequestMapping("/edit.do")
    public ModelAndView edit(String id) throws Exception {
        ModelAndView mv=new ModelAndView();
        //找出角色可以添加的权限
        List<Permission> permissionCanAdd = roleService.findPermissionCanAdd(id);
        //查找要编辑的用户信息
        Role role = roleService.findById(id);
        mv.addObject("permissionCanAdd",permissionCanAdd);
        mv.addObject("role",role);
        mv.setViewName("role-edit");
        return mv;
    }

    /**
     * 用户信息修改
     * @return
     */
    @RequestMapping("/update.do")
    public String update(Role role,String[] permissionIds) throws Exception {

        ModelAndView mv=new ModelAndView();
        //用户信息修改
        roleService.update(role);

        //角色信息修改
        roleService.updatePermission(role.getId(),permissionIds);
        return "redirect:findAll.do";

    }


    /**
     * 角色添加权限，查找可以添加的权限
     * @return
     */
    @RequestMapping("/findRoleByIdAndAllPermission.do")
    public ModelAndView findRoleByIdAndAllPermission(@RequestParam(name = "id",required = true) String id) throws Exception {
        ModelAndView mv=new ModelAndView();

        Role role = roleService.findById(id);
        List<Permission> permissionCanAdd = roleService.findPermissionCanAdd(id);

        mv.addObject("role",role);
        mv.addObject("permissionCanAdd",permissionCanAdd);

        mv.setViewName("role-permission-add");

        return mv;
    }

    //角色添加权限
    @RequestMapping("/addPermissionToRole.do")
    public String addPermissionToRole(@RequestParam(value = "roleId",required = true) String roleId,
                                      @RequestParam(value = "ids",required = true)String[] permissionIds) throws Exception {

        roleService.addPermission(roleId,permissionIds);
        return "redirect:findAll.do";
    }


}
