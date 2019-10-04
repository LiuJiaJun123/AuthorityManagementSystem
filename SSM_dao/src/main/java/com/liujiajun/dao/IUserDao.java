package com.liujiajun.dao;

import com.liujiajun.domain.Role;
import com.liujiajun.domain.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public interface IUserDao {

    /**
     * 登录时
     * @param username
     * @return
     * @throws Exception
     */
    @Select("select * from users where username=#{username}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "username",property = "username"),
            @Result(column = "email",property = "email"),
            @Result(column = "password",property = "password"),
            @Result(column = "phoneNum",property = "phoneNum"),
            @Result(column = "status",property = "status"),
            @Result(column = "id",property = "roles",javaType = List.class,many = @Many(select = "com.liujiajun.dao.IRoleDao.findRoleByUserId"))
    })
    public UserInfo findByUsername(String username) throws Exception;


    /**
     * 查找所有用户
     * @param page
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Select("select * from users")
    public List<UserInfo> findAll(int page,int pageSize) throws Exception;


    /**
     * 保存用户信息
     * @param userInfo
     * @throws Exception
     */
    @Insert("insert into users(email,username,password,phoneNum,status) " +
            "values(#{email},#{username},#{password},#{phoneNum},#{status})")
    public void save(UserInfo userInfo) throws Exception;


    /**
     * 添加用户时，查看用户名是否存在
     * @param username
     * @return
     * @throws Exception
     */
    @Select("select * from users where username=#{username}")
    public UserInfo findNameExist(String username) throws Exception;


    /**
     * 查看用户详情
     * @param id
     * @return
     * @throws Exception
     */
    @Select("select * from users where id=#{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "username",property = "username"),
            @Result(column = "email",property = "email"),
            @Result(column = "password",property = "password"),
            @Result(column = "phoneNum",property = "phoneNum"),
            @Result(column = "status",property = "status"),
            @Result(column = "id",property = "roles",many = @Many(select = "com.liujiajun.dao.IRoleDao.findRoleByUserId")),
    })
    public UserInfo findById(String id) throws Exception;


    /**
     * 查找该用户可以添加的角色，从角色表查找 该用户没有的角色
     * @param
     * @return
     */
    @Select("select * from role where id not in(select roleId from users_role where userId=#{id})")
    public List<Role> findRoleCanAdd(String id);


    //用户添加角色
    @Insert("insert into users_role(userId,roleId) values(#{userId},#{roleId})")
    public void addRole(@Param("userId") String userId, @Param("roleId") String roleId);


    //删除用户
    @Delete("delete from users where id=#{id}")
    void delete(String id);

    //根据姓名查找用户
    @Select("select * from users where username=#{username}")
    UserInfo findByName(String username);

    //用户信息修改
    @Update("update users set username=#{username},email=#{email},password=#{password},phoneNum=#{phoneNum},status=#{status} where id=#{id}")
    void update(UserInfo userInfo);

    //用户删除角色
    @Delete("delete from users_role where userId=#{id}")
    void deleteRole(String id);
}
