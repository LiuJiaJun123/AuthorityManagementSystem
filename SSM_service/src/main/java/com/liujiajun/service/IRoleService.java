package com.liujiajun.service;

import com.liujiajun.domain.Permission;
import com.liujiajun.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IRoleService {


    public List<Role> findAll(int page,int pageSize) throws Exception;

    //添加角色
    public void save(Role role) throws Exception;

    //删除角色
    void delete(String[] selectIds);

    Role findById(String id) throws Exception;

    /**
     * 查找该角色可以添加的权限，从权限表查找 该角色没有的权限
     * @param
     * @return
     */
    List<Permission> findPermissionCanAdd(String id) throws Exception;

    //角色添加权限
    void addPermission(String roleId, String[] permissionIds) throws Exception;

    /**
     * 查看角色详情
     * @param id
     * @return
     * @throws Exception
     */
    public Role findByRoleId(String id) throws Exception;

    //角色信息修改
    void update(Role role);

    //角色的资源权限修改
    void updatePermission(String id, String[] permissionIds) throws Exception;

    //根据角色名查找
    Role findByName(String roleName) throws Exception;
}
