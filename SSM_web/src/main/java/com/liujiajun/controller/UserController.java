package com.liujiajun.controller;

import com.github.pagehelper.PageInfo;
import com.liujiajun.domain.CheckUsername;
import com.liujiajun.domain.Role;
import com.liujiajun.domain.UserInfo;
import com.liujiajun.exception.CustomException;
import com.liujiajun.service.IRoleService;
import com.liujiajun.service.IUserService;
import javafx.scene.input.InputMethodTextRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @RequestMapping("/findAll.do")
//    @PreAuthorize("hasRole('ADMIN')")
//    @RolesAllowed("ADMIN")
    public ModelAndView findAll(@RequestParam(value = "page",required = true,defaultValue = "1") Integer page,
                                @RequestParam(value = "pageSize",required = true,defaultValue = "4")Integer pageSize) throws Exception {

        ModelAndView mv=new ModelAndView();
        List<UserInfo> userList = userService.findAll(page,pageSize);
        PageInfo usersInfo=new PageInfo(userList);
        mv.addObject("usersInfo",usersInfo);
        mv.setViewName("user-list");
        return mv;

    }

    /**
     * 添加用户，查找所有角色
     */
    @RequestMapping("/findAllRole.do")
    public ModelAndView findAllRole() throws Exception {

        ModelAndView mv=new ModelAndView();

        List<Role> roleList = roleService.findAll(1, 20);

        mv.addObject("roleList",roleList);
        mv.setViewName("user-add");
        return mv;
    }


    //添加用户
    @RequestMapping("/save.do")
    public String save(UserInfo userInfo,String[] roleIds) throws Exception {

        ModelAndView mv=new ModelAndView();
        userService.save(userInfo);
        //因为id是随机生成的，所以要根据姓名查找用户
        userInfo=userService.findByName(userInfo.getUsername());
        userService.addRole(userInfo.getId(),roleIds);
        return "redirect:findAll.do";

    }

    /**
     * 添加用户时，查看用户名是否已经已经存在
     * @param checkUsername
     * @return
     * @throws Exception
     */
    @RequestMapping("/findByName.do")
    @ResponseBody
    public CheckUsername findByName(@RequestBody CheckUsername checkUsername) throws Exception{

//        CheckUsername checkUsername=new CheckUsername();
        System.out.println("UserController 1111111:"+checkUsername);

        Boolean flag = userService.findNameExist(checkUsername.getUsername());
        if(!flag){
            //用户已经存在
            checkUsername.setFlag(false);
            checkUsername.setErrorMsg("该用户名已经存在，请重新输入！");
        }else {
            //可以注册
            checkUsername.setFlag(true);
            checkUsername.setErrorMsg("");
        }
        return checkUsername;
    }


    //删除用户
    @RequestMapping("delete.do")
    public String delete(String[] selectIds) throws Exception {

        userService.delete(selectIds);

        return "redirect:findAll.do";

    }


    /**
     * 查看用户详情
     * @return
     */
    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) throws Exception {
        ModelAndView mv=new ModelAndView();
        UserInfo user = userService.findById(id);
        mv.addObject("user",user);
        mv.setViewName("user-show");
        return mv;
    }

    /**
     * 用户信息编辑
     * @return
     */
    @RequestMapping("/edit.do")
    public ModelAndView edit(String id) throws Exception {
        ModelAndView mv=new ModelAndView();
        //找出可以用户添加的角色
        List<Role> roleCanAdd = userService.findRoleCanAdd(id);
        //查找要编辑的用户信息
        UserInfo user = userService.findById(id);
        mv.addObject("roleCanAdd",roleCanAdd);
        mv.addObject("user",user);
        mv.setViewName("user-edit");
        return mv;
    }

    /**
     * 用户信息修改
     * @return
     */
    @RequestMapping("/update.do")
    public String update(UserInfo userInfo,String[] roleIds) throws Exception {

        ModelAndView mv=new ModelAndView();
        //用户信息修改
        userService.update(userInfo);

        //角色信息修改
        userService.updateRole(userInfo.getId(),roleIds);
        return "redirect:findAll.do";

    }



    /**
     * 用户添加角色，查找可以添加的角色
     * @param id
     * @return
     */
//    @RequestMapping("/findUserByIdAndAllRole.do")
//    public ModelAndView findUserByIdAndAllRole(String id) throws Exception {
//
//        ModelAndView mv=new ModelAndView();
//
//        UserInfo user = userService.findById(id);
//        List<Role> roleCanAdd = userService.findRoleCanAdd(id);
//
//        mv.addObject("user",user);
//        mv.addObject("roleCanAdd",roleCanAdd);
//        mv.setViewName("user-role-add");
//        return mv;
//    }

    /**
     * 用户添加角色
     * @return
     */
    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(value = "userId",required = true)String userId,
                                @RequestParam(value = "ids",required = true) String[] roleIds) {

        userService.addRole(userId,roleIds);
        return "redirect:findAll.do";
    }


}
