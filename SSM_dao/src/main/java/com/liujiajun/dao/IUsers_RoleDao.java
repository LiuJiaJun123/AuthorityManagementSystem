package com.liujiajun.dao;

import com.liujiajun.domain.Users_Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("users_roleDao")
public interface IUsers_RoleDao {

    //根据用户id删除
    @Delete("delete from users_role where userId=#{userId}")
    public void deleteByUserId(String userId);

    //根据角色id查找所有（删除角色时，先查询是否有用户拥有该角色）
    @Select("select * from users_role where roleId=#{roleId}")
    public List<Users_Role> findByRoleId(String roleId);

    //根据角色id删除
    @Delete("delete from users_role where roleId=#{roleId}")
    void deleteByRoleId(String roleId);
}
