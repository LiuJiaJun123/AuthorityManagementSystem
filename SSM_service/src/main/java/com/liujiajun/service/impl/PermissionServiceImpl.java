package com.liujiajun.service.impl;

import com.github.pagehelper.PageHelper;
import com.liujiajun.dao.IPermissionDao;
import com.liujiajun.dao.IRoleDao;
import com.liujiajun.dao.IRole_PermissionDao;
import com.liujiajun.domain.Permission;
import com.liujiajun.domain.Role;
import com.liujiajun.service.IPermissionService;
import com.liujiajun.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("permissionService")
public class PermissionServiceImpl implements IPermissionService {


    @Autowired
    private IPermissionDao permissionDao;

    @Autowired
    private IRole_PermissionDao role_permissionDao;

    @Override
    public List<Permission> findAll(int page,int pageSize) throws Exception {

        PageHelper.startPage(page,pageSize);
        return permissionDao.findAll();
    }

    //根据id查找
    @Override
    public Permission findById(String id) {
        return permissionDao.findById(id);
    }

    //添加
    @Override
    public void save(Permission permission) throws Exception {
        permissionDao.save(permission);
    }

    //删除
    @Override
    public void delete(String[] selectIds) {
        for (String id : selectIds) {
            //1、先从role_permission删除记录
            role_permissionDao.deleteByPermissionId(id);

            //2、再从permission表删除记录
            permissionDao.delete(id);
        }
    }

    //修改
    @Override
    public void update(Permission permission) {
        permissionDao.update(permission);
    }


}
