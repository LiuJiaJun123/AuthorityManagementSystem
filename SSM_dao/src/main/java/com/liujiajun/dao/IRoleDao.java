package com.liujiajun.dao;

import com.liujiajun.domain.Permission;
import com.liujiajun.domain.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("roleDao")
public interface IRoleDao {

    @Select("select * from role where id in (select roleId from users_role where userId=#{userId})")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "roleName",property = "roleName"),
            @Result(column = "roleDesc",property = "roleDesc"),
            @Result(column = "id",property = "permissions",many = @Many(select = "com.liujiajun.dao.IPermissionDao.findPermissionByRoleId") )
    })
    public List<Role> findRoleByUserId(String userId) throws Exception;


    @Select("select * from role")
    public List<Role> findAll() throws Exception;


    //添加角色
    @Insert("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    public void save(Role role);


    //删除角色
    @Delete("delete from role where id=#{id}")
    void delete(String id);


    @Select("select * from role where id=#{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "roleName",property = "roleName"),
            @Result(column = "roleDesc",property = "roleDesc"),
            @Result(column = "id",property = "permissions",many = @Many(select = "com.liujiajun.dao.IPermissionDao.findPermissionByRoleId")),
    })
    Role findById(String id) throws Exception;


    /**
     * 查看角色详情
     * @param id
     * @return
     * @throws Exception
     */
    @Select("select * from role where id=#{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "roleName",property = "roleName"),
            @Result(column = "roleDesc",property = "roleDesc"),
            @Result(column = "id",property = "permissions",many = @Many(select = "com.liujiajun.dao.IPermissionDao.findPermissionByRoleId")),
    })
    Role findByRoleId(String id) throws Exception;



    /**
     * 查找该角色可以添加的权限，从权限表查找 该角色没有的权限
     * @param
     * @return
     */
    @Select("select * from permission where id not in (select permissionId from role_permission where roleId=#{id})")
    List<Permission> findPermissionCanAdd(String id) throws Exception;

    /**
     * 角色添加权限
     * @param roleId
     * @param permissionId
     */
    @Insert(("insert into role_permission(roleId,permissionId) values(#{roleId},#{permissionId})"))
    void addPermission(@Param("roleId") String roleId, @Param("permissionId") String permissionId) throws Exception;

    //用户信息修改
    @Update("update role set roleName=#{roleName},roleDesc=#{roleDesc} where id=#{id}")
    void update(Role role);

    //删除资源权限
    @Delete("delete from role_permission where roleId=#{id}")
    void deletePermission(String id);

    //根据角色名查找
    @Select("select * from role where roleName=#{roleName}")
    Role findByName(String roleName) throws Exception;
}
