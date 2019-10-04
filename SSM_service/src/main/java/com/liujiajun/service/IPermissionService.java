package com.liujiajun.service;

import com.liujiajun.domain.Permission;
import com.liujiajun.domain.Role;

import java.util.List;

public interface IPermissionService {

    public List<Permission> findAll(int page, int pageSize) throws Exception;

    //根据id查找
    Permission findById(String id);

    //添加
    public void save(Permission permission) throws Exception;

//    删除
    void delete(String[] selectIds);

    //修改
    void update(Permission permission);
}
