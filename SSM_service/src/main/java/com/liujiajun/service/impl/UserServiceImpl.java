package com.liujiajun.service.impl;

import com.github.pagehelper.PageHelper;
import com.liujiajun.BCryptPasswordEncoderUtils;
import com.liujiajun.dao.IPermissionDao;
import com.liujiajun.dao.IRole_PermissionDao;
import com.liujiajun.dao.IUserDao;
import com.liujiajun.dao.IUsers_RoleDao;
import com.liujiajun.domain.Permission;
import com.liujiajun.domain.Role;
import com.liujiajun.domain.UserInfo;
import com.liujiajun.service.IUserService;
import com.sun.org.apache.bcel.internal.generic.IUSHR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IUsers_RoleDao users_roleDao;

    @Autowired
    private IPermissionDao permissionDao;

    //可以配置加密类bean 也可以不配置加密类，使用BCryptPasswordEncoderUtils的encode方法
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo=null;
        try {
            userInfo = userDao.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
          }
        List<Role> roles = userInfo.getRoles();
        List<SimpleGrantedAuthority> authoritys = null;
        try {
            authoritys = getAuthority(roles);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //密码未加密时，需要加"{noop}"
//        User user=new User(userInfo.getUsername(),"{noop}"+userInfo.getPassword(),userInfo.getStatus()==0?false:true,
//                true,true,true,authoritys);

        //密码加密后，不需要加"{noop}"
        User user=new User(userInfo.getUsername(),userInfo.getPassword(),userInfo.getStatus()==0?false:true,
                true,true,true,authoritys);
         return user;
    }
    //作用是返回一个List集合，集合中装入的是角色描述
    private List<SimpleGrantedAuthority> getAuthority(List<Role> roles) throws Exception {
        List<SimpleGrantedAuthority> authoritys = new ArrayList();
        for (Role role : roles) {

            List<Permission> permissions = permissionDao.findPermissionByRoleId(role.getId());
            for (Permission permission : permissions) {
                authoritys.add(new SimpleGrantedAuthority(permission.getUrl()));
            }
//            authoritys.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
//        System.out.println("aaaaaaaaaaaaa:"+authoritys);
        return authoritys;
    }


    @Override
    public List<UserInfo> findAll(int page,int pageSize) throws Exception {
        PageHelper.startPage(page,pageSize);
        return userDao.findAll(page,pageSize);
    }


    @Override
    public void save(UserInfo userInfo) throws Exception {

        //可以配置加密类bean
        userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));

        //使用BCryptPasswordEncoderUtils的encode方法
//        userInfo.setPassword(BCryptPasswordEncoderUtils.encodePassword(userInfo.getPassword()));
        userDao.save(userInfo);
    }

    //添加用户时，查看用户是否已经存在
    @Override
    public Boolean findNameExist(String username) throws Exception {
        UserInfo user = userDao.findNameExist(username);
        if(user!=null){
            //用户名已经存在，不可注册
            return false;
        }
        //用户名不存在，可以注册
        return true;
    }



    @Override
    public UserInfo findById(String id) throws Exception {
        return userDao.findById(id);
    }

    //根据姓名查找用户
    @Override
    public UserInfo findByName(String username) throws Exception {
        return userDao.findByName(username);
    }

    //删除用户
    @Override
    public void delete(String[] selectIds) {
        for (String id : selectIds) {
            //先从users_role删除记录
            users_roleDao.deleteByUserId(id);
            //再从users表删除记录
            userDao.delete(id);
        }
    }


    //查找可以添加的角色
    @Override
    public List<Role> findRoleCanAdd(String id) throws Exception {

        return userDao.findRoleCanAdd(id);
    }

    //用户添加角色
    @Override
    public void addRole(String userId, String[] roleIds) {

        for (String roleId : roleIds) {
            userDao.addRole(userId,roleId);
        }

    }

    //用户信息修改
    @Override
    public void update(UserInfo userInfo) throws Exception {
        //如果密码被更改，就进行密码加密
        UserInfo user = userDao.findById(userInfo.getId());
        if( ! user.getPassword().equals(userInfo.getPassword())){
            //密码加密
            userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        }
        userDao.update(userInfo);
    }

    //用户的角色修改
    @Override
    public void updateRole(String id, String[] roleIds) {
        //先从users_role表删除原来的
        userDao.deleteRole(id);
        //接着再插入新的
        for (String roleId : roleIds) {
            userDao.addRole(id,roleId);
        }
    }


}
