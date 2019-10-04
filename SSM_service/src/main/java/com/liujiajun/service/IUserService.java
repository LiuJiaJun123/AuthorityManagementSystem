package com.liujiajun.service;

import com.liujiajun.domain.Role;
import com.liujiajun.domain.UserInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IUserService extends UserDetailsService {

    public List<UserInfo> findAll(int page,int pageSize) throws Exception;

    //添加用户
    public void save(UserInfo userInfo) throws Exception;

    //添加用户时，查看用户是否已经存在
    public Boolean findNameExist(String username) throws Exception;

    //根据id查找用户
    public UserInfo findById(String id) throws Exception;

    //根据姓名查找用户
    UserInfo findByName(String username) throws Exception;

    //删除用户
    void delete(String[] selectIds);

    //查找可以添加的角色
    public List<Role> findRoleCanAdd(String id) throws Exception;

    //用户添加角色
    public void addRole(String userId, String[] roleIds);

    //用户信息修改
    void update(UserInfo userInfo) throws Exception;

    //用户的角色修改
    void updateRole(String id, String[] roleIds);
}
