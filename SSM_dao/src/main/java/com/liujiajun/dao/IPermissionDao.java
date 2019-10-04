package com.liujiajun.dao;

import com.liujiajun.domain.Permission;
import com.liujiajun.domain.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("permissionDao")
public interface IPermissionDao {


    @Select("select * from permission where id in (select permissionId from role_permission where roleId=#{roleId})")
    public List<Permission> findByRoleId(String roleId) throws Exception;

    @Select("select * from permission")
    public List<Permission> findAll() throws Exception;

    //根据id查找
    @Select("select * from permission where id=#{id}")
    Permission findById(String id);

    //保存
    @Insert("insert into permission(permissionName,url) values(#{permissionName},#{url})")
    public void save(Permission permission) throws Exception;

    //删除
    @Delete("delete from permission where id=#{id}")
    void delete(String id);


    //根据角色id查找权限
    @Select("select * from permission where id in (select permissionId from role_permission where roleId=#{roleId})")
    public List<Permission> findPermissionByRoleId(String roleId) throws Exception;

    //修改
    @Update("update permission set permissionName=#{permissionName},url=#{url} where id=#{id}")
    void update(Permission permission);
}
