package com.liujiajun.dao;

import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

@Repository("role_permissionDao")
public interface IRole_PermissionDao {

    //根据角色id删除
    @Delete("delete from role_permission where roleId=#{roleId}")
    void deleteByRoleId(String roleId);

    //根据资源权限id删除
    @Delete("delete from role_permission where permissionId=#{permissionId}")
    void deleteByPermissionId(String permissionId);

}
