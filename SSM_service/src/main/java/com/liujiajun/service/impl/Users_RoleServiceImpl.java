package com.liujiajun.service.impl;

import com.liujiajun.dao.IUsers_RoleDao;
import com.liujiajun.service.IUsers_RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("/users_roleService")
public class Users_RoleServiceImpl implements IUsers_RoleService {

    @Autowired
    private IUsers_RoleDao users_roleDao;

    //根据用户id删除
    @Override
    public void deleteByUserId(String userId){
        users_roleDao.deleteByUserId(userId);
    }

}
